package com.github.insanusmokrassar.iobject.realisations;

import com.github.insanusmokrassar.iobject.interfaces.IObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleIObject implements IObject {
    protected Map<String, Object> objects;

    public SimpleIObject(Map<String, Object> from) {
        objects = new HashMap<>(from);
    }

    public SimpleIObject() {
        objects = new HashMap<>();
    }

    @Override
    public Boolean put(String key, Object value) {
        objects.put(key, value);
        return true;
    }

    @Override
    public Object get(String key) {
        return objects.get(key);
    }

    @Override
    public Boolean remove(String key) {
        return objects.remove(key) != null;
    }

    @Override
    public Set<String> keys() {
        return objects.keySet();
    }
}
