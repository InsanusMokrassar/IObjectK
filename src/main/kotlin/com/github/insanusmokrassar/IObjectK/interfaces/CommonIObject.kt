package com.github.insanusmokrassar.IObjectK.interfaces

interface CommonIObject<KeyType, ValueType> : IInputObject<KeyType, ValueType>, IOutputObject<KeyType, ValueType>

typealias IObject<T> = CommonIObject<String, T>
