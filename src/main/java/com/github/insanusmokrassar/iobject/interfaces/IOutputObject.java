package com.github.insanusmokrassar.iobject.interfaces;

import com.github.insanusmokrassar.iobject.exceptions.OutputException;

/**
 * Use this interface for output operations such as writing into some output stream or object
 * @param <KeyType> Type of keys
 * @param <ValueType> Type of values
 */
public interface IOutputObject<KeyType, ValueType> {
    /**
     * Try to put value with key value
     * @param key Key to set
     * @param value Value to set
     * @throws OutputException Throws when can't set this pair
     */
    void put(KeyType key, ValueType value) throws OutputException;

    /**
     * Try to remove value using key
     * @param key Key to remove
     * @throws OutputException Throws when can't remove this value
     */
    void remove(KeyType key) throws OutputException;
}
