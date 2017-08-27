package com.github.insanusmokrassar.IObjectK.realisations

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.interfaces.IObject

class SimpleTypedIObject<T: Any>: IObject<T> {
    private val value = HashMap<String, T>()
    override fun <ResType: T> get(key: String): ResType {
        if (!value.containsKey(key)) {
            throw ReadException("Field $key is not in object")
        }
        return value[key]!! as ResType
    }

    override fun put(key: String, value: T) {
        this.value.put(key, value)
    }

    override fun keys(): Set<String> {
        return value.keys
    }

    override fun putAll(toPutMap: Map<String, T>) {
        value.putAll(toPutMap)
    }

    override fun remove(key: String) {
        value.remove(key)
    }
}