package com.github.insanusmokrassar.IObjectK.realisations

import com.github.insanusmokrassar.IObjectK.interfaces.IObject

open class SimpleTypedIObject<T: Any>: IObject<T>, SimpleCommonIObject<String, T> {
    constructor(from: Map<String, T>) : super(from)
    constructor(from: IObject<T>) : super(from)
    constructor() : super()
}