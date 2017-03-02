package com.github.insanusmokrassar.iobject.realisations;

import com.github.insanusmokrassar.iobject.exceptions.ReadException;
import com.github.insanusmokrassar.iobject.exceptions.WriteException;
import com.github.insanusmokrassar.iobject.interfaces.IObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleIObject<T> implements IObject<T> {
    protected final Map<String, T> objects;

    public SimpleIObject(Map<String, T> from) {
        objects = new HashMap<>(from);
    }

    public SimpleIObject(IObject<T> from) {
        this();
        if (from == null) {
            return;
        }
        for (String key : from.keys()) {
            try {
                objects.put(key, from.get(key));
            } catch (ReadException e) {
                //TODO: add exception handling
            }
        }
    }

    public SimpleIObject() {
        objects = new HashMap<>();
    }

    @Override
    public void put(String key, T value) throws WriteException {
        objects.put(key, value);
    }

    @Override
    public void putAll(Map<String, T> toPutMap) throws WriteException {
        objects.putAll(toPutMap);
    }

    @Override
    public T get(String key) throws ReadException {
        T object = objects.get(key);
        if (object == null) {
            throw new ReadException("Can't return value - value from key(" + key + ") - is null");
        }
        return object;
    }

    @Override
    public void remove(String key) throws WriteException {
        if (objects.remove(key) == null) {
            throw new WriteException("Can't remove value for key(" + key + ")");
        }
    }

    @Override
    public Set<String> keys() {
        return objects.keySet();
    }

    @Override
    public String toString() {
        return objects.toString();
    }

    @Override
    protected SimpleIObject<T> clone() throws CloneNotSupportedException {
        return new SimpleIObject<>(this);
    }
}
