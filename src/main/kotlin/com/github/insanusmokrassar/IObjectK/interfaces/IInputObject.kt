package com.github.insanusmokrassar.IObjectK.interfaces

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException

interface IInputObject<KeyType, ValueType> {

    /**
     * @param key Key for getting data
     * @return Object or null
     */
    @Throws(ReadException::class)
    fun <T : ValueType>get(key : KeyType) : T

    /**
     * @return Set of the keys. Can't be null but can be empty
     */
    fun keys() : Set<KeyType>
}

fun <K, V> IInputObject<K, V>.has(key: K): Boolean {
    return keys().contains(key)
}
