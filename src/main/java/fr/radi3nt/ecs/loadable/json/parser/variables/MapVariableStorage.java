package fr.radi3nt.ecs.loadable.json.parser.variables;

import fr.radi3nt.json.JsonValue;

import java.util.Map;

public class MapVariableStorage implements VariableStorage {

    private final Map<String, JsonValue> storage;

    public MapVariableStorage(Map<String, JsonValue> storage) {
        this.storage = storage;
    }

    @Override
    public JsonValue getReference(String variableName) {
        return storage.get(variableName);
    }
}
