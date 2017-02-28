package com.github.insanusmokrassar.iobject.realisations;

import com.github.insanusmokrassar.iobject.exceptions.InputException;
import com.github.insanusmokrassar.iobject.exceptions.OutputException;

import java.util.Map;
import java.util.Set;

public class ConcurrentSimpleObject<T> extends SimpleIObject<T>{

    public ConcurrentSimpleObject(Map<String, T> from) {
        super(from);
    }

    public ConcurrentSimpleObject() {
        super();
    }

    @Override
    public void put(String key, T value) throws OutputException {
        synchronized (objects) {
            super.put(key, value);
        }
    }

    @Override
    public T get(String key) throws InputException {
        synchronized (objects) {
            return super.get(key);
        }
    }

    @Override
    public void remove(String key) throws OutputException {
        synchronized (objects) {
            super.remove(key);
        }
    }

    @Override
    public Set<String> keys() {
        synchronized (objects) {
            return super.keys();
        }
    }
}
