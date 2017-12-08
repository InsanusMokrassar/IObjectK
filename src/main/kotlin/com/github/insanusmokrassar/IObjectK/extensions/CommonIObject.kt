package com.github.insanusmokrassar.IObjectK.extensions

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject

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
