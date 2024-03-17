package fr.radi3nt.ecs.loadable.persistence.loader.loaders;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.loadable.persistence.loader.TypeCheckingPersistentComponentLoader;
import fr.radi3nt.ecs.persistence.data.MappedPersistentData;
import fr.radi3nt.ecs.persistence.data.PersistentData;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;

public abstract class MappedPersistentComponentLoader extends TypeCheckingPersistentComponentLoader {
    @Override
    public Component load(PersistentData data) throws ComponentPersistenceException {
        MappedPersistentData mappedPersistentData = safelyCastData(data, MappedPersistentData.class);
        return loadInternal(mappedPersistentData);
    }

    protected abstract Component loadInternal(MappedPersistentData mappedPersistentData) throws ComponentPersistenceException;
}
