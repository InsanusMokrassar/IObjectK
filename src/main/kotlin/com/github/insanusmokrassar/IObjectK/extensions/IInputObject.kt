package com.github.insanusmokrassar.IObjectK.extensions

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectK.interfaces.has

/**
 * Creating map to IInputObject
 */
fun <K, V> IInputObject<K, V>.asMap(): Map<K, V> {
    return IInputObjectMap(this)
}

/**
 * Mapping commands of map to commands of IInputObject
 */
internal open class IInputObjectMap<K, V>(
        private val source : IInputObject<K, V>
) : Map<K, V> {

    override val entries: Set<Map.Entry<K, V>>
        get() {
            val set = HashSet<Map.Entry<K, V>>()
            source.keys().forEach {
                set.add(
                        IInputObjectEntry(it, source)
                )
            }
            return set
        }

    override val keys: Set<K>
        get() = source.keys()

    override val size: Int
        get() = source.size

    override val values: Collection<V>
        get() = entries.map { it.value }

    override fun containsKey(key: K): Boolean =
            source.has(key)

    override fun containsValue(value: V): Boolean =
            values.firstOrNull { it == value } != null

    override fun get(key: K): V? =
            try {
                source.get(key)
            } catch (e: ReadException) {
                null
            }

    override fun isEmpty(): Boolean =
            size == 0

}


private class IInputObjectEntry<out K, out V>(
        override val key: K,
        private val targetObject: IInputObject<K, V>
) : Map.Entry<K, V> {
    override val value: V
        get() = targetObject.get(key) ?: throw IllegalStateException("Value is absent")
}
