package com.github.insanusmokrassar.IObjectK.utils

import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleCommonIObject


operator fun <K, V> IInputObject<K, V>.plus(other: IInputObject<K, V>) : CommonIObject<K, V> {
    val result = SimpleCommonIObject(this)
    other.forEach {
        result[it.first] = it.second
    }
    return result
}

operator fun <K, V> CommonIObject<K, V>.plusAssign(other: IInputObject<K, V>) {
    other.forEach {
        this[it.first] = it.second
    }
}

operator fun <K, V> IInputObject<K, V>.minus(other: IInputObject<K, V>): CommonIObject<K, V> {
    val result = SimpleCommonIObject(this)
    other.forEach {
        if (result.contains(it)) {
            result.remove(it.first)
        }
    }
    return result
}

operator fun <K, V> CommonIObject<K, V>.minusAssign(other: IInputObject<K, V>) {
    other.forEach {
        if (this.contains(it)) {
            this.remove(it.first)
        }
    }
}

operator fun <K, V> IInputObject<K, V>.contains(other: IInputObject<K, V>): Boolean {
    return keys().containsAll(other.keys()) && other.keys().firstOrNull {
        other.get<V>(it) == get<V>(it)
    } == null
}