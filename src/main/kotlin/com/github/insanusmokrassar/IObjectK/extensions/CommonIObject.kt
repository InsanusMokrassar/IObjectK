package com.github.insanusmokrassar.IObjectK.extensions

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
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
        source.put(key, value)
        return old
    }

    override fun putAll(from: Map<out K, V>) {
        from.forEach {
            source.put(it.key, it.value)
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
        get() = source.get(key) ?: throw IllegalStateException("Value is absent")

    override fun setValue(newValue: V): V {
        val old = source.get<V>(key)
        source.put(key, newValue)
        return old
    }
}


private fun CommonIObject<String, in Any>.remap(
        key: String,
        sourceIObject: CommonIObject<String, Any>,
        destObject: CommonIObject<String, Any>
): Boolean {
    return try {
        val rule = get<String>(key)// "keyToPut": "path/to/get/field"
        rule.split(delimiter).let {
            destObject[key] = sourceIObject.get(it)
        }
        true
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
            remap(
                    key,
                    sourceObject,
                    destObject
            )
        } catch (e: ReadException) {
            try {
                val childRules = get<IObject<Any>>(key)
                try {
                    destObject.get<IObject<Any>>(key)
                } catch (e: ReadException) {
                    SimpleIObject().also {
                        destObject[key] = it
                    }
                }.let {
                    childDest ->
                    childRules.remap(sourceObject, childDest)
                }
            } catch (e: ReadException) {
                try {
                    val rules = get<List<String>>(key)// think that it is array of keys in source object
                    val iterator = rules.iterator()
                    while (iterator.hasNext()) {
                        val ruleKey = iterator.next()
                        val remapResult: Boolean = remap(
                                ruleKey,
                                sourceObject,
                                destObject
                        )
                        if (remapResult) {
                            return
                        }
                    }
                } catch (e: ReadException) {
                    Logger.getGlobal().warning("Can't remap key $key of $this")
                }
            }
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
                else -> "\"$it\""
            }
        } ?: "null"
    }
}

fun IInputObject<String, in Any>.toJsonString(): String {
    return keys().joinToString(",", "{", "}") {
        val value = get<Any>(it)
        val valueString = when(value) {
            is IInputObject<*, *> -> (value as? IInputObject<String, in Any>) ?. toJsonString() ?: value.toString()
            is Iterable<*> -> value.toJsonString()
            else -> "\"$value\""
        }
        "\"$it\":$valueString"
    }
}

val delimiter = "/"


val lastIdentifier = "last"

fun String.toPath(): List<String> {
    return this.split(delimiter).filter { it.isNotEmpty() }
}

fun CommonIObject<String, in Any>.put(path: List<String>, value: Any) {
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
                            currentParent.add(it.toInt(), value)
                        }
                    }
                }
                else -> (currentParent as IObject<Any>)[it]=value
            }
        } else {
            currentParent = when (currentParent) {
                is List<*> -> (currentParent as List<*>)[it.toInt()]!!
                else -> (currentParent as IObject<Any>)[it]
            }
        }
    }
}

fun CommonIObject<String, in Any>.get(path: List<String>): Any {
    var current: Any = this
    path.forEach {
        current = when (current) {
            is List<*> -> (current as List<*>)[it.toInt()]!!
            else -> (current as IObject<Any>)[it]
        }
    }
    return current
}

fun CommonIObject<String, in Any>.cut(path: List<String>): Any {
    var current: Any = this
    path.forEach {
        val parent = current
        current = when (current) {
            is List<*> -> (current as List<*>)[it.toInt()]!!
            else -> (current as IObject<Any>)[it]
        }
        if (path.last() == it) {
            when (parent) {
                is MutableList<*> -> (parent as MutableList<Any>).remove(it.toInt())
                else -> (parent as IObject<Any>).remove(it)
            }
        }
    }
    return current
}