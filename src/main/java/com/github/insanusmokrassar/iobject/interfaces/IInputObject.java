package com.github.insanusmokrassar.iobject.interfaces;

import com.github.insanusmokrassar.iobject.exceptions.ReadException;

import java.util.Set;

/**
 * Use this interface for input operations such as reading from some input stream or object
 * @param <KeyType> Type of keys
 * @param <ValueType> Type of values
 */
public interface IInputObject<KeyType, ValueType>{
    /**
     * @param key Key for getting data
     * @return Object
     * @throws ReadException Throws when object not found
     */
    ValueType get(KeyType key) throws ReadException;

    /**
     * @return Set of the keys. Can't be null but can be empty
     */
    Set<KeyType> keys();
}
