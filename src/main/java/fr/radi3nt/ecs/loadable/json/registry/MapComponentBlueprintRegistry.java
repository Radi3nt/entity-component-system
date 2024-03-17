package fr.radi3nt.ecs.loadable.json.registry;

import java.util.Map;

public class MapComponentBlueprintRegistry implements ComponentBlueprintRegistry {

    private final Map<String, ComponentPersistenceType> types;

    public MapComponentBlueprintRegistry(Map<String, ComponentPersistenceType> types) {
        this.types = types;
    }

    @Override
    public ComponentPersistenceType get(String name) {
        return types.get(name);
    }
}
