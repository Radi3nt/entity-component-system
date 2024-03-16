package fr.radi3nt.ecs.loadable.persistence.loader;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.persistence.blueprint.comp.ComponentBlueprint;
import fr.radi3nt.ecs.persistence.data.MappedPersistentData;
import fr.radi3nt.ecs.persistence.data.PersistentData;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectionPersistentComponentLoader extends TypeCheckingPersistentComponentLoader implements PersistentComponentLoader {

    private final ComponentBlueprint blueprint;

    public ReflectionPersistentComponentLoader(ComponentBlueprint blueprint) {
        this.blueprint = blueprint;
    }

    @Override
    public Component load(PersistentData data) throws ComponentPersistenceException {
        Component component = blueprint.create();

        MappedPersistentData safeData = safelyCastData(data, MappedPersistentData.class);
        try {
            for (Map.Entry<String, Object> stringObjectEntry : safeData.getKeyValue()) {
                Object value = stringObjectEntry.getValue();
                Field field = component.getClass().getDeclaredField(stringObjectEntry.getKey());
                field.setAccessible(true);
                field.set(component, value);
            }
        } catch (Exception e) {
            throw new ComponentPersistenceException("Failed to load component using reflection", e);
        }

        return component;
    }
}
