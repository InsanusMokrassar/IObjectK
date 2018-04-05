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
        sourceIObject: IObject<Any>,
        destObject: CommonIObject<String, Any>
): Boolean {
    val rule = get<String>(key)// "keyToPut": "path/to/get/field"
    rule.split(delimiter).let {
        return try {
            destObject[key] = sourceIObject.get(it)
            true
        } catch (e: ReadException) { // object by key in source object was not found
            false
        } catch (e: IndexOutOfBoundsException) { // object by key in array of source object was not found
            false
        }
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
        sourceObject: IObject<Any>,
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

