package com.github.insanusmokrassar.iobject.realisations;

import com.github.insanusmokrassar.iobject.exceptions.ReadException;
import com.github.insanusmokrassar.iobject.exceptions.WriteException;
import com.github.insanusmokrassar.iobject.interfaces.IObject;

import java.util.Map;
import java.util.Set;

public class ConcurrentSimpleIObject<T> extends SimpleIObject<T>{

    public ConcurrentSimpleIObject(Map<String, T> from) {
        super(from);
    }

    public ConcurrentSimpleIObject(IObject<T> from) {
        super(from);
    }

    public ConcurrentSimpleIObject() {
        super();
    }

    @Override
    synchronized public void put(String key, T value) throws WriteException {
        super.put(key, value);
    }

    @Override
    synchronized public void putAll(Map<String, T> toPutMap) throws WriteException {
        super.putAll(toPutMap);
    }

    @Override
    synchronized public T get(String key) throws ReadException {
        return super.get(key);
    }

    @Override
    synchronized public void remove(String key) throws WriteException {
        super.remove(key);
    }

    @Override
    synchronized public Set<String> keys() {
        return super.keys();
    }

    @Override
    protected SimpleIObject<T> clone() throws CloneNotSupportedException {
        return new ConcurrentSimpleIObject<>(this);
    }
}
