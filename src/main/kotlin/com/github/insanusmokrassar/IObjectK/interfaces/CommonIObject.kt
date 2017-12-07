package com.github.insanusmokrassar.IObjectK.interfaces

interface CommonIObject<KeyType, ValueType> : MutableMap<KeyType, ValueType> {
    fun <R : ValueType> getTyped(key : KeyType) : R?
}
