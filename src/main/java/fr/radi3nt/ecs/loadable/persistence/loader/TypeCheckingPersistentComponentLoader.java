package fr.radi3nt.ecs.loadable.persistence.loader;

import fr.radi3nt.ecs.persistence.data.PersistentData;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;

public abstract class TypeCheckingPersistentComponentLoader implements PersistentComponentLoader {

    protected <T extends PersistentData> T safelyCastData(PersistentData data, Class<T> dataClass) throws ComponentPersistenceException {
        if (!(dataClass.isAssignableFrom(data.getClass())))
            throw new ComponentPersistenceException("Persistent data is not of right type: expected '" + dataClass.getSimpleName() + " and got '" + data.getClass().getSimpleName() + "'");
        return (T) data;
    }

}
