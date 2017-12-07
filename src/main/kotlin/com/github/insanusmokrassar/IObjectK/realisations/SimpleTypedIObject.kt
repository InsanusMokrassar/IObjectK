package com.github.insanusmokrassar.IObjectK.realisations

import com.github.insanusmokrassar.IObjectK.interfaces.IObject

open class SimpleTypedIObject<T: Any>: IObject<T>, SimpleCommonIObject<String, T> {
    constructor(from: Map<String, T>) : super(from.toMutableMap())
    constructor() : super()
}