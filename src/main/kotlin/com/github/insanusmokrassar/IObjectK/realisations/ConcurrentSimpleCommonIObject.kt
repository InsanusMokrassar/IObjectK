package com.github.insanusmokrassar.IObjectK.realisations

import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject

/**
 * Realisation which provide unconcurrent access to object data
 */
class ConcurrentSimpleCommonIObject<K, V> : SimpleCommonIObject<K, V> {

    constructor(from: Map<K, V>) : super(from)

    constructor(from: CommonIObject<K, V>) : super(from)

    constructor() : super()

    override val size: Int
        @Synchronized get() = super.size

    @Synchronized override fun set(key: K, value: V) {
        super.set(key, value)
    }

    @Synchronized override fun putAll(toPutMap: Map<K, V>) {
        super.putAll(toPutMap)
    }

    @Synchronized override fun <T : V> get(key: K): T {
        return super.get(key)
    }

    @Synchronized override fun remove(key: K) {
        super.remove(key)
    }

    @Synchronized override fun keys(): Set<K> {
        return super.keys()
    }

    @Synchronized override fun toString(): String {
        return super.toString()
    }
}