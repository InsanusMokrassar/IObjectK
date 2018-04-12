package com.github.insanusmokrassar.IObjectK.extensions

import com.github.insanusmokrassar.IObjectK.interfaces.IObject

fun List<Any>.doRecursively(action: (IObject<Any>) -> Unit) {
    forEach {
        when(it) {
            is IObject<*> -> (it as? IObject<Any>) ?.let { it.doRecursively(action) }
            is List<*> -> (it as? List<Any>) ?.let { it.doRecursively(action) }
        }
    }
}

fun IObject<Any>.doRecursively(action: (IObject<Any>) -> Unit) {
    action(this)
    keys().forEach {
        try {
            get<IObject<Any>>(it).doRecursively(action)
        } catch (e: ClassCastException) {
            try {
                get<List<Any>>(it).doRecursively(action)
            } catch (e: ClassCastException) {}
        }
    }
}
