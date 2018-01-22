package com.github.insanusmokrassar.IObjectK.realisations

import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject

class StandardIInputObjectIterator<out K, out V>(
        inputObject: IInputObject<K, V>
) : Iterator<Pair<K, V>> {
    private val inputObject = SimpleCommonIObject(inputObject)
    private val keyIterator = inputObject.keys().iterator()

    override fun hasNext(): Boolean {
        return keyIterator.hasNext()
    }

    override fun next(): Pair<K, V> {
        val key = keyIterator.next()
        val value: V = inputObject[key]
        return Pair(key, value)
    }
}