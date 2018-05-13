package com.github.insanusmokrassar.IObjectK.interfaces

/**
 * Interface which declare objects which include mutable and immutable parts of IObject and not limited by types of keys/values
 */
interface CommonIObject<K, V> : IInputObject<K, V>, IOutputObject<K, V>
