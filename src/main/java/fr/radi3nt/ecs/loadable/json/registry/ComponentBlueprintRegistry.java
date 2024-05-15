package fr.radi3nt.ecs.loadable.json.registry;

import fr.radi3nt.ecs.loadable.json.exceptions.ComponentPersistenceTypeNotFound;

public interface ComponentBlueprintRegistry {

    ComponentPersistenceType get(String name) throws ComponentPersistenceTypeNotFound;

}
