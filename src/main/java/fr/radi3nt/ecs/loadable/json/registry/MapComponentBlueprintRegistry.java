package fr.radi3nt.ecs.loadable.json.registry;

import fr.radi3nt.ecs.loadable.json.exceptions.ComponentPersistenceTypeNotFound;

import java.util.Map;

public class MapComponentBlueprintRegistry implements ComponentBlueprintRegistry {

    private final Map<String, ComponentPersistenceType> types;

    public MapComponentBlueprintRegistry(Map<String, ComponentPersistenceType> types) {
        this.types = types;
    }

    @Override
    public ComponentPersistenceType get(String name) {
        ComponentPersistenceType type = types.get(name);
        if (type==null)
            throw new ComponentPersistenceTypeNotFound("Could not find persistence type for id '" + name + "'");
        return type;
    }
}
