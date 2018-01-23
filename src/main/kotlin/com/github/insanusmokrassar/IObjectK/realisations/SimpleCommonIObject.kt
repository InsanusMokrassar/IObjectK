package com.github.insanusmokrassar.IObjectK.realisations

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.exceptions.WriteException
import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import java.util.HashMap

open class SimpleCommonIObject <K, V> : CommonIObject<K, V> {
    protected val objects: MutableMap<K, V>

    override val size: Int
        get() = objects.size

    constructor(from: Map<K, V>) {
        objects = HashMap(from)
    }

    constructor(from: IInputObject<K, V>) : this() {
        for (key in from.keys()) {
            objects[key] = from.get(key)
        }
    }

    constructor() {
        objects = HashMap()
    }

    @Throws(WriteException::class)
    override fun set(key: K, value: V) {
        value ?.let {
            objects[key] = it
        } ?: objects.remove(key)
    }

    @Throws(WriteException::class)
    override fun putAll(toPutMap: Map<K, V>) {
        toPutMap.forEach {
            set(it.key, it.value)
        }
    }

    @Throws(ReadException::class)
    override fun <T : V> get(key: K): T {
        val toReturn = objects[key] ?: throw ReadException("Can't return value - value from key($key) - is null")
        return toReturn as T
    }

    @Throws(WriteException::class)
    override fun remove(key: K) {
        if (objects.remove(key) == null) {
            throw WriteException("Can't remove value for key($key)")
        }
    }

    override fun keys(): Set<K> = objects.keys

    override fun toString(): String = objects.toString()
}
