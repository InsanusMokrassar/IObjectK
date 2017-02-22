package com.github.insanusmokrassar.iobject.interfaces;

import java.util.Set;

public interface IInputObject<KeyType, ValueType>{
    ValueType get(KeyType key);
    Set<KeyType> keys();
}
