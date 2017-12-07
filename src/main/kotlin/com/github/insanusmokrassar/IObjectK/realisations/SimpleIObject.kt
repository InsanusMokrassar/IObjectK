package com.github.insanusmokrassar.IObjectK.realisations

import com.github.insanusmokrassar.IObjectK.interfaces.IObject

open class SimpleIObject : IObject<Any>, SimpleTypedIObject<Any> {
    constructor(from: Map<String, Any>) : super(from)
    constructor() : super()
}