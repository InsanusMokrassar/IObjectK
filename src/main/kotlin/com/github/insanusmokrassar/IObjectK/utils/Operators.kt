package com.github.insanusmokrassar.IObjectK.utils

import com.github.insanusmokrassar.IObjectK.extensions.iterator
import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectK.interfaces.has
import com.github.insanusmokrassar.IObjectK.realisations.SimpleCommonIObject


operator fun <K, V> IInputObject<K, V>.plus(other: IInputObject<K, V>) : CommonIObject<K, V> {
    val result = SimpleCommonIObject(this)
    other.iterator().forEach {
        result[it.first] = it.second
    }
    return result
}

operator fun <K, V> CommonIObject<K, V>.plusAssign(other: IInputObject<K, V>) {
    other.iterator().forEach {
        this[it.first] = it.second
    }
}

operator fun <K, V> IInputObject<K, V>.minus(other: IInputObject<K, V>): CommonIObject<K, V> {
    val result = SimpleCommonIObject(this)
    other.iterator().forEach {
        if (result.has(it.first) && result.get<V>(it.first) == it.second) {
            result.remove(it.first)
        }
    }
    return result
}

operator fun <K, V> CommonIObject<K, V>.minusAssign(other: IInputObject<K, V>) {
    other.iterator().forEach {
        if (has(it.first) && get<V>(it.first) == it.second) {
            this.remove(it.first)
        }
    }
}

operator fun <K, V> IInputObject<K, V>.contains(other: IInputObject<K, V>): Boolean {
    return keys().containsAll(other.keys()) && other.keys().firstOrNull {
        other.get<V>(it) == get<V>(it)
    } == null
}