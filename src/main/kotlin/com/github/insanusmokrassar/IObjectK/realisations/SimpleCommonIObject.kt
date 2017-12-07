package com.github.insanusmokrassar.IObjectK.realisations

import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject
import java.util.HashMap

open class SimpleCommonIObject <K, V>(
        delegate: MutableMap<K, V>
) : CommonIObject<K, V>, MutableMap<K, V> by delegate {
    constructor() : this(HashMap<K, V>())

    override fun <R : V> getTyped(key: K): R? {
        return get(key) as? R
    }
}
