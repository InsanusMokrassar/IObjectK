package com.github.insanusmokrassar.IObjectK.extensions

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.interfaces.IOutputObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleIObject
import java.util.logging.Logger

/**
 * Creating mutable map to CommonIObject
 */
fun <K, V> CommonIObject<K, V>.asMutableMap(): Map<K, V> {
    return IInputObjectMap(this)
}

internal class CommonIObjectMap<K, V>(
        private val source: CommonIObject<K, V>
) : MutableMap<K, V>, IInputObjectMap<K, V>(source) {
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() {
            val set = HashSet<MutableMap.MutableEntry<K, V>>()
            source.keys().forEach {
                set.add(
                        CommonIObjectEntry(it, source)
                )
            }
            return set
        }

    override val keys: MutableSet<K>
        get() = source.keys().toMutableSet()

    override val values: MutableCollection<V>
        get() = entries.map { it.value }.toMutableList()

    override fun clear() {
        keys.forEach {
            source.remove(it)
        }
    }

    override fun put(key: K, value: V): V? {
        val old =try {
            source.get<V>(key)
        } catch (e: ReadException) {
            null
        }
        source[key] = value
        return old
    }

    override fun putAll(from: Map<out K, V>) {
        from.forEach {
            source[it.key] = it.value
        }
    }

    override fun remove(key: K): V? {
        val old =try {
            source.get<V>(key)
        } catch (e: ReadException) {
            null
        }
        source.remove(key)
        return old
    }
}

private class CommonIObjectEntry<K, V>(
        override val key: K,
        private val source: CommonIObject<K, V>
) : MutableMap.MutableEntry<K, V> {
    override val value: V
        get() = source[key] ?: throw IllegalStateException("Value is absent")

    override fun setValue(newValue: V): V {
        val old = source.get<V>(key)
        source[key] = newValue
        return old
    }
}

@Throws(ReadException::class)
private fun CommonIObject<String, in Any>.remap(
        key: String,
        sourceIObject: CommonIObject<String, Any>,
        destObject: CommonIObject<String, Any>
): Boolean {
    return try {
        val rule = get<String>(key)// "keyToPut": "path/to/get/field"
        remapByValue(key, rule, sourceIObject, destObject)
    } catch (e: ReadException) {
        throw e
    } catch (e: Exception) {
        false
    }
}

private fun remapByValue(
        key: String,
        rule: String,
        sourceIObject: CommonIObject<String, Any>,
        destObject: CommonIObject<String, Any>
): Boolean {
    return try {
        rule.toPath().let {
            destObject[key] = sourceIObject.get(it)
        }
        true
    } catch (e: ReadException) {
        throw e
    } catch (e: Exception) {
        false
    }
}

/**
 * [this] - remap rules in next format:
 * <pre>
 *     {
 *          "key": "target/source/key/to/get/value",
 *          "key1": ["target","source","key","to","get","value"],
 *          "key2": rules map which keys will be used as "key2/ruleKey"
 *     }
 * </pre>
 */
fun CommonIObject<String, in Any>.remap(
        sourceObject: CommonIObject<String, Any>,
        destObject: CommonIObject<String, Any>
) {
    keys().forEach {
        key ->
        try {
            if (!remap(key, sourceObject, destObject)) {
                try {
                    val childRules = get<IObject<Any>>(key)
                    try {
                        destObject.get<IObject<Any>>(key)
                    } catch (e: Exception) {
                        SimpleIObject().also {
                            destObject[key] = it
                        }
                    }.let { childDest ->
                        childRules.remap(sourceObject, childDest)
                    }
                } catch (e: Exception) {
                    val rules = get<List<String>>(key)// think that it is array of keys in source object
                    val iterator = rules.iterator()
                    while (iterator.hasNext()) {
                        val rule = iterator.next()
                        try {
                            val remapResult: Boolean = remapByValue(
                                    key,
                                    rule,
                                    sourceObject,
                                    destObject
                            )
                            if (remapResult) {
                                break
                            }
                        } catch (e: ReadException) {
                            continue
                        }
                    }
                }
            }
        } catch (e: ReadException) {
            Logger.getGlobal().throwing(
                    "IObject#Extensions",
                    "remap",
                    e
            )
        }
    }
}

private fun Iterable<*>.toJsonString(): String {
    return joinToString(",", "[", "]") {
        it ?.let {
            when (it) {
                is IInputObject<*, *> -> (it as? IInputObject<String, in Any>) ?. toJsonString() ?: it.toString()
                is Iterable<*> -> it.toJsonString()
                is Number -> it.toString()
                is Boolean -> it.toString()
                else -> "\"${it.toString().replace("\"", "\\\"")}\""
            }
        } ?: "null"
    }
}

/**
 * Convert [IInputObject] to JSON formatted [String]
 */
fun IInputObject<String, in Any>.toJsonString(): String {
    return keys().joinToString(",", "{", "}") {
        val value = get<Any>(it)
        val valueString = when(value) {
            is IInputObject<*, *> -> (value as? IInputObject<String, in Any>) ?. toJsonString() ?: value.toString()
            is Iterable<*> -> value.toJsonString()
            else -> "\"${value.toString().replace("\"", "\\\"")}\""
        }
        "\"${it.replace("\"", "\\\"")}\":$valueString"
    }
}

private const val delimiter = "/"


private const val lastIdentifier = "last"

/**
 * Convert receiver to path which can be used in operations with iterable keys
 */
fun String.toPath(): List<String> {
    return this.split(delimiter).filter { it.isNotEmpty() }
}

/**
 * Put [value] by [path] into object. In path identifiers as "0" can be used to choose object/array in array or to put in specified position
 */
fun <T> CommonIObject<T, in Any>.put(path: List<T>, value: Any) {
    var currentParent: Any = this
    path.forEach {
        if (path.last() == it) {
            when (currentParent) {
                is List<*> -> {
                    (currentParent as? MutableList<Any>)?.let {
                        currentParent ->
                        if (it == lastIdentifier) {
                            currentParent.add(value)
                        } else {
                            currentParent.add(it.toString().toInt(), value)
                        }
                    }
                }
                is IInputObject<*, *> -> (currentParent as IOutputObject<T, Any>)[it] = value
                else -> throw IllegalStateException("Can't get value by key: $it; It is not list or IOutputObject")
            }
        } else {
            currentParent = when (currentParent) {
                is List<*> -> (currentParent as List<*>)[it.toString().toInt()]!!
                is IInputObject<*, *> -> (currentParent as CommonIObject<T, Any>)[it]
                else -> throw IllegalStateException("Can't get value by key: $it; It is not list or CommonIObject")
            }
        }
    }
}

/**
 * Get value by [path] in object. In path identifiers as "0" can be used to choose object/array in array
 */
fun <T, R: Any> IInputObject<T, in Any>.get(path: List<T>): R {
    var current: Any = this
    path.forEach {
        current = when (current) {
            is List<*> -> (current as List<*>)[it.toString().toInt()]!!
            is IInputObject<*, *> -> (current as IInputObject<T, Any>)[it]
            else -> throw IllegalStateException("Can't get value by key: $it; It is not list or CommonIObject")
        }
    }
    return current as R
}

/**
 * Cut value by [path] in object. In path identifiers as "0" can be used to choose object/array in array
 */
fun <T, R: Any> CommonIObject<T, in Any>.cut(path: List<T>): R {
    var current: Any = this
    path.forEach {
        val parent = current
        current = when (current) {
            is List<*> -> (current as List<*>)[it.toString().toInt()]!!
            is CommonIObject<*, *> -> (current as CommonIObject<T, Any>)[it]
            else -> throw IllegalStateException("Can't get value by key: $it; It is not list or CommonIObject")
        }
        if (path.last() == it) {
            when (parent) {
                is MutableList<*> -> (parent as MutableList<Any>).remove(it.toString().toInt())
                is CommonIObject<*, *> -> (parent as CommonIObject<T, Any>).remove(it)
                else -> throw IllegalStateException("Can't get value by key: $it; It is not list or CommonIObject")
            }
        }
    }
    return current as R
}