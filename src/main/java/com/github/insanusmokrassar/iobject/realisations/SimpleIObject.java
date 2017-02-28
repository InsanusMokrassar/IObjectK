package com.github.insanusmokrassar.iobject.realisations;

import com.github.insanusmokrassar.iobject.exceptions.InputException;
import com.github.insanusmokrassar.iobject.exceptions.OutputException;
import com.github.insanusmokrassar.iobject.interfaces.CommonIObject;
import com.github.insanusmokrassar.iobject.interfaces.IObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleIObject<T> implements IObject<T> {
    protected final Map<String, T> objects;

    public SimpleIObject(Map<String, T> from) {
        objects = new HashMap<>(from);
    }

    public SimpleIObject() {
        objects = new HashMap<>();
    }

    @Override
    public void put(String key, T value) throws OutputException {
        objects.put(key, value);
    }

    @Override
    public T get(String key) throws InputException {
        T object = objects.get(key);
        if (object == null) {
            throw new InputException("Can't return value - value from key(" + key + ") - is null");
        }
        return object;
    }

    @Override
    public void remove(String key) throws OutputException{
        if (objects.remove(key) == null) {
            throw new OutputException("Can't remove value for key(" + key + ")");
        }
    }

    @Override
    public Set<String> keys() {
        return objects.keySet();
    }
}
