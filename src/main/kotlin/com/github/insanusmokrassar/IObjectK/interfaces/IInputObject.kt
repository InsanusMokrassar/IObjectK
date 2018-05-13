package com.github.insanusmokrassar.IObjectK.interfaces

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.realisations.StandardIInputObjectIterator

interface IInputObject<K, V> : Iterable<Pair<K, V>>{
    /**
     * Sum of records on object
     */
    val size: Int
        get() = keys().size

    /**
     * @param key Key for getting data
     * @return Object or null
     */
    @Throws(ReadException::class)
    operator fun <T: V> get(key : K) : T

    /**
     * @return Set of the keys. Can't be null but can be empty
     */
    fun keys() : Set<K>

    /**
     * Return [Iterator] object which can be used for forEach, for example
     */
    override fun iterator(): Iterator<Pair<K, V>> {
        return StandardIInputObjectIterator(this)
    }
}

/**
 * @return true if this objectcontains this key
 */
fun <K, V> IInputObject<K, V>.has(key: K): Boolean {
    return keys().contains(key)
}
