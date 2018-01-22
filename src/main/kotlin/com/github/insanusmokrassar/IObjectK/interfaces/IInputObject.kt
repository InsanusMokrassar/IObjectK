package com.github.insanusmokrassar.IObjectK.interfaces

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException

interface IInputObject<K, in V> {

    /**
     * Return sum of records on object
     */
    val size: Int
        get() = keys().size

    /**
     * @param key Key for getting data
     * @return Object or null
     */
    @Throws(ReadException::class)
    fun <T: V> get(key : K) : T

    /**
     * @return Set of the keys. Can't be null but can be empty
     */
    fun keys() : Set<K>
}

fun <K, V> IInputObject<K, V>.has(key: K): Boolean {
    return keys().contains(key)
}

fun <K, V> IInputObject<K, V>.contain(other: IInputObject<K, V>): Boolean {
    return keys().containsAll(other.keys()) && other.keys().firstOrNull {
        other.get<V>(it) == get<V>(it)
    } == null
}
