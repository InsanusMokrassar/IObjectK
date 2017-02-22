package com.github.insanusmokrassar.iobject.interfaces;

import java.util.Set;

public interface CommonIObject<KeyType, ValueType> {
    Boolean put(KeyType key, ValueType value);
    ValueType get(KeyType key);
    Boolean remove(KeyType key);
    Set<KeyType> keys();
}
