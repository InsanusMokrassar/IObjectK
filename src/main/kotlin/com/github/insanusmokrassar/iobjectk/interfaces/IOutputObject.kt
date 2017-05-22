package com.github.insanusmokrassar.iobjectk.interfaces

import com.github.insanusmokrassar.iobjectk.exceptions.WriteException

interface IOutputObject<KeyType, ValueType> {

    /**
     * Try to put value with key value
     * @param key Key to set
     * *
     * @param value Value to set
     * *
     * @throws WriteException Throws when can't set this pair
     */
    @Throws(WriteException::class)
    fun put(key: KeyType, value: ValueType)

    /**
     * Try to put value with key value
     * @param toPutMap map to set
     * *
     * @throws WriteException Throws when can't set this pair
     */
    @Throws(WriteException::class)
    fun putAll(toPutMap: Map<KeyType, ValueType>)

    /**
     * Try to remove value using key
     * @param key Key to remove
     * *
     * @throws WriteException Throws when can't remove this value
     */
    @Throws(WriteException::class)
    fun remove(key: KeyType)
}