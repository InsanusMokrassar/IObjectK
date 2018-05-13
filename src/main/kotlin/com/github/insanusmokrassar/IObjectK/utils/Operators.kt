package com.github.insanusmokrassar.IObjectK.utils

import com.github.insanusmokrassar.IObjectK.extensions.iterator
import com.github.insanusmokrassar.IObjectK.interfaces.CommonIObject
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectK.interfaces.has
import com.github.insanusmokrassar.IObjectK.realisations.SimpleCommonIObject

/**
 * @return new [CommonIObject] with both values from first and second objects (second object in priority)
 */
operator fun <K, V> IInputObject<K, V>.plus(other: IInputObject<K, V>) : CommonIObject<K, V> {
    val result = SimpleCommonIObject(this)
    other.iterator().forEach {
        result[it.first] = it.second
    }
    return result
}

/**
 * Put values from [other] object into recipient
 */
operator fun <K, V> CommonIObject<K, V>.plusAssign(other: IInputObject<K, V>) {
    other.iterator().forEach {
        this[it.first] = it.second
    }
}

/**
 * @return new [CommonIObject] which contain only keys which is not contained in first object
 */
operator fun <K, V> IInputObject<K, V>.minus(other: IInputObject<K, V>): CommonIObject<K, V> {
    val result = SimpleCommonIObject(this)
    other.iterator().forEach {
        if (result.has(it.first) && result.get<V>(it.first) == it.second) {
            result.remove(it.first)
        }
    }
    return result
}

/**
 * Remove from recipient all keys and values which
 */
operator fun <K, V> CommonIObject<K, V>.minusAssign(other: IInputObject<K, V>) {
    other.iterator().forEach {
        if (has(it.first) && get<V>(it.first) == it.second) {
            this.remove(it.first)
        }
    }
}

/**
 * @return true if receiver contains all keys and values of [other]
 */
operator fun <K, V> IInputObject<K, V>.contains(other: IInputObject<K, V>): Boolean {
    return keys().containsAll(other.keys()) && other.keys().firstOrNull {
        other.get<V>(it) == get<V>(it)
    } == null
}