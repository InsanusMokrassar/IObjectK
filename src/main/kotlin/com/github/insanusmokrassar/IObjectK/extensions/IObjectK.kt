package com.github.insanusmokrassar.IObjectK.extensions

import com.github.insanusmokrassar.IObjectK.interfaces.IObject


val lastIdentifier = "last"

val delimiter = "/"

fun String.toPath(): List<String> {
    return this.split(delimiter).filter { it.isNotEmpty() }
}

fun IObject<Any>.put(path: List<String>, value: Any) {
    var currentParent: Any = this
    path.forEach {
        if (path.last() == it) {
            currentParent = when (currentParent) {
                is List<*> -> (currentParent as List<*>)[it.toInt()]!!
                else -> (currentParent as IObject<Any>).get(it)
            }
        } else {
            when (currentParent) {
                is List<*> -> {
                    (currentParent as? MutableList<Any>)?.let {
                        currentParent ->
                        if (it == lastIdentifier) {
                            currentParent.add(value)
                        } else {
                            currentParent.add(it.toInt(), value)
                        }
                    }
                }
                else -> (currentParent as IObject<Any>).put(it, value)
            }
        }
    }
}

fun IObject<Any>.get(path: List<String>): Any {
    var current: Any = this
    path.forEach {
        current = when (current) {
            is List<*> -> (current as List<*>)[it.toInt()]!!
            else -> (current as IObject<Any>).get(it)
        }
    }
    return current
}

fun IObject<Any>.cut(path: List<String>): Any {
    var current: Any = this
    path.forEach {
        val parent = current
        current = when (current) {
            is List<*> -> (current as List<*>)[it.toInt()]!!
            else -> (current as IObject<Any>).get(it)
        }
        if (path.last() == it) {
            when (parent) {
                is MutableList<*> -> (parent as MutableList<Any>).remove(it.toInt())
                else -> (parent as IObject<Any>).remove(it)
            }
        }
    }
    return current
}
