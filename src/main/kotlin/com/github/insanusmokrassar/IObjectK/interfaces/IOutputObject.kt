package com.github.insanusmokrassar.IObjectK.interfaces

import com.github.insanusmokrassar.IObjectK.exceptions.WriteException

interface IOutputObject<K, in V> {
    /**
     * Try to put value with key value
     * @param key Key to set
     * *
     * @param value Value to set
     * *
     * @throws WriteException Throws when can't set this pair
     */
    @Throws(WriteException::class)
    operator fun set(key: K, value: V)

    /**
     * Try to put value with key value
     * @param toPutMap map to set
     * *
     * @throws WriteException Throws when can't set those pairs
     */
    @Throws(WriteException::class)
    fun putAll(toPutMap: Map<K, V>)

    /**
     * Try to remove value using key
     * @param key Key to remove
     * *
     * @throws WriteException Throws when can't remove this value
     */
    @Throws(WriteException::class)
    fun remove(key: K)
}
