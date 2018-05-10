package com.github.insanusmokrassar.IObjectK

import com.github.insanusmokrassar.IObjectK.extensions.duplicate
import com.github.insanusmokrassar.IObjectK.realisations.SimpleIObject

fun main(args: Array<String>) {
    SimpleIObject().apply {
        set("example1", SimpleIObject().apply { this["example1.2"] = 1.2 })
        set("example2", listOf("2.1", 2.2, true))
        println(this.apply {
            println(hashCode())
            println(get<Any>("example1").hashCode())
        })
        println(this.duplicate().apply {
            println(hashCode())
            println(get<Any>("example1").hashCode())
        })
    }
}
