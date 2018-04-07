package com.github.insanusmokrassar.IObjectK.realisations

import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectK.interfaces.IObject

open class SimpleTypedIObject<T>: IObject<T>, SimpleCommonIObject<String, T> {
    constructor(from: Map<String, T>) : super(from)
    constructor(from: IInputObject<String, T>) : super(from)
    constructor() : super()
}