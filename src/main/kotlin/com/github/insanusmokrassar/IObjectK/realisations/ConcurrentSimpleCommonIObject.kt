package com.github.insanusmokrassar.IObjectK.realisations

import com.github.insanusmokrassar.IObjectK.exceptions.ReadException
import com.github.insanusmokrassar.IObjectK.exceptions.WriteException
import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject

class ConcurrentSimpleCommonIObject<K, V> : SimpleCommonIObject<K, V> {

    constructor(from: Map<K, V>) : super(from)

    constructor(from: CommonIObject<K, V>) : super(from)

    constructor() : super()

    @Synchronized @Throws(WriteException::class)
    override fun put(key: K, value: V) {
        super.put(key, value)
    }

    @Synchronized @Throws(WriteException::class)
    override fun putAll(toPutMap: Map<K, V>) {
        super.putAll(toPutMap)
    }

    @Synchronized @Throws(ReadException::class)
    override operator fun <T: V> get(key: K): T {
        return super.get(key)
    }

    @Synchronized @Throws(WriteException::class)
    override fun remove(key: K) {
        super.remove(key)
    }

    @Synchronized override fun keys(): Set<K> {
        return super.keys()
    }
}