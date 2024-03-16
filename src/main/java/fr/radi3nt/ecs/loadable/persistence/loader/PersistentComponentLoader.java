package fr.radi3nt.ecs.loadable.persistence.loader;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.persistence.data.PersistentData;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;

public interface PersistentComponentLoader {

    Component load(PersistentData data) throws ComponentPersistenceException;

}
