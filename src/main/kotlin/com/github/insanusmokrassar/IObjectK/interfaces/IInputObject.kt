package com.github.insanusmokrassar.IObjectK.interfaces

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.utils.contains

interface IInputObject<K, V> {

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
    operator fun <T: V> get(key : K) : T

    /**
     * @return Set of the keys. Can't be null but can be empty
     */
    fun keys() : Set<K>
}

/**
 * @return true if this objectcontains this key
 */
fun <K, V> IInputObject<K, V>.has(key: K): Boolean {
    return keys().contains(key)
}

@Deprecated("This method was replaced and will be remover in near versions", ReplaceWith("contains"))
fun <K, V> IInputObject<K, V>.contain(other: IInputObject<K, V>): Boolean = contains(other)

