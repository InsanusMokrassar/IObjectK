package com.github.insanusmokrassar.IObjectK.extensions

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleCommonIObject

val lastIdentifier = "last"

val delimiter = "/"

fun String.toPath(): List<String> {
    return this.split(delimiter).filter { it.isNotEmpty() }
}

fun IObject<Any>.put(path: Iterable<String>, value: Any) {
    var currentParent: Any = this
    path.forEach {
        if (path.last() == it) {
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
        } else {
            currentParent = when (currentParent) {
                is List<*> ->
                    (currentParent as List<*>)[it.toInt()]
                            ?: throw IllegalArgumentException("Have no element with number $it")
                is IObject<*> -> (currentParent as IObject<Any>).getTyped(it) ?: {
                    val current = currentParent as IObject<Any>
                    val futureCurrent = SimpleCommonIObject<String, Any>()
                    current.put(it, futureCurrent)
                    futureCurrent
                }()
                else -> throw IllegalArgumentException("Illegal type of object for key \"$it\" and current $currentParent")
            }
        }
    }
}

fun <T : Any> IObject<T>.get(path: Iterable<String>): T? {
    var current: Any = this
    path.forEach {
        current = when (current) {
            is List<*> -> (current as List<*>)[it.toInt()]
            else -> (current as IObject<Any>).getTyped(it)
        } ?: return null
    }
    return current as? T
}

fun IObject<Any>.cut(path: Iterable<String>): Any? {
    var current: Any = this
    path.forEach {
        val parent = current
        current = when (current) {
            is List<*> -> (current as List<*>)[it.toInt()]
            else -> (current as IObject<Any>).getTyped(it)
        } ?: return null
        if (path.last() == it) {
            when (parent) {
                is MutableList<*> -> (parent as MutableList<Any>).remove(it.toInt())
                else -> (parent as IObject<Any>).remove(it)
            }
        }
    }
    return current
}
