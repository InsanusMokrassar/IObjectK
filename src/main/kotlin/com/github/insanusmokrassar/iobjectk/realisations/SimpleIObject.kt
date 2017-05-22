package com.github.insanusmokrassar.iobjectk.realisations

import com.github.insanusmokrassar.iobjectk.exceptions.ReadException
import com.github.insanusmokrassar.iobjectk.exceptions.WriteException
import com.github.insanusmokrassar.iobjectk.interfaces.IObject
import java.util.HashMap

open class SimpleIObject : IObject<Any> {
    protected val objects: MutableMap<String, Any>

    constructor(from: Map<String, Any>) {
        objects = HashMap(from)
    }

    constructor(from: IObject<Any>?) : this() {
        if (from == null) {
            return
        }
        for (key in from.keys()) {
            try {
                objects.put(key, from.get(key))
            } catch (e: ReadException) {
                //TODO: add exception handling
            }

        }
    }

    constructor() {
        objects = HashMap<String, Any>()
    }

    @Throws(WriteException::class)
    override fun put(key: String, value: Any) {
        objects.put(key, value)
    }

    @Throws(WriteException::class)
    override fun putAll(toPutMap: Map<String, Any>) {
        objects.putAll(toPutMap)
    }

    @Throws(ReadException::class)
    override fun <T : Any>get(key: String): T {
        val toReturn = objects[key] ?: throw ReadException("Can't return value - value from key($key) - is null")
        return toReturn as T
    }

    @Throws(WriteException::class)
    override fun remove(key: String) {
        if (objects.remove(key) == null) {
            throw WriteException("Can't remove value for key($key)")
        }
    }

    override fun keys(): Set<String> {
        return objects.keys
    }

    override fun toString(): String {
        return objects.toString()
    }
}