package fr.radi3nt.ecs.persistence.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MappedPersistentData implements PersistentData {

    private final Map<String, Object> keyValue;

    public MappedPersistentData(Map<String, Object> keyValue) {
        this.keyValue = keyValue;
    }

    public Object get(String key) {
        return keyValue.get(key);
    }

    public String[] getKeys() {
        return keyValue.keySet().toArray(new String[0]);
    }

    public Set<Map.Entry<String, Object>> getKeyValue() {
        return keyValue.entrySet();
    }

}
