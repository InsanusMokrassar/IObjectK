package com.github.insanusmokrassar.iobject.interfaces;

public interface IOutputObject<KeyType, ValueType> {
    Boolean put(KeyType key, ValueType value);
    Boolean remove(KeyType key);
}
