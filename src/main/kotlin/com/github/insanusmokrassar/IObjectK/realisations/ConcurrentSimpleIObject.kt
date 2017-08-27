package com.github.insanusmokrassar.IObjectK.realisations

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.exceptions.WriteException
import com.github.insanusmokrassar.IObjectK.interfaces.IObject

class ConcurrentSimpleIObject : SimpleIObject {

    constructor(from: Map<String, Any>) : super(from) {}

    constructor(from: IObject<Any>) : super(from) {}

    constructor() : super() {}

    @Synchronized @Throws(WriteException::class)
    override fun put(key: String, value: Any) {
        super.put(key, value)
    }

    @Synchronized @Throws(WriteException::class)
    override fun putAll(toPutMap: Map<String, Any>) {
        super.putAll(toPutMap)
    }

    @Synchronized @Throws(ReadException::class)
    override operator fun <T : Any>get(key: String): T {
        return super.get<T>(key)
    }

    @Synchronized @Throws(WriteException::class)
    override fun remove(key: String) {
        super.remove(key)
    }

    @Synchronized override fun keys(): Set<String> {
        return super.keys()
    }
}