package fr.radi3nt.ecs.world.handle;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.entity.HandledEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalMapHandle implements ComponentWorldHandle {

    private final Map<Class<? extends Component>, Component> entityComponents = new ConcurrentHashMap<>();
    private final WorldComponentStorage worldComponentStorage;

    public LocalMapHandle(WorldComponentStorage worldComponentStorage) {
        this.worldComponentStorage = worldComponentStorage;
    }

    private void addComponent(ECSEntity entity, Component component) {
        component.add(entity);
        worldComponentStorage.addComponent(component);
    }

    private void removeComponent(Component component) {
        component.remove();
        worldComponentStorage.removeComponent(component);
    }

    @Override
    public void add(ECSEntity entity, Component component) {
        Component old = entityComponents.put(component.getClass(), component);
        if (old != null) {
            removeComponent(old);
        }
        addComponent(entity, component);
    }

    @Override
    public void add(ECSEntity entity, Component... components) {
        for (Component component : components) {
            add(entity, component);
        }
    }

    @Override
    public void add(ECSEntity entity, Collection<? extends Component> components) {
        for (Component component : components) {
            add(entity, component);
        }
    }

    @Override
    public void remove(ECSEntity entity, Component component) {
        remove(entity, component.getClass());
    }

    @Override
    public void remove(ECSEntity entity, Class<? extends Component> componentClass) {
        Component removed = entityComponents.remove(componentClass);
        if (removed!=null)
            removeComponent(removed);
    }

    @Override
    public <T extends Component> T get(ECSEntity entity, Class<T> componentClass) {
        return (T) entityComponents.get(componentClass);
    }

    @Override
    public Collection<Component> get(HandledEntity handledEntity) {
        return Collections.unmodifiableCollection(entityComponents.values());
    }

    @Override
    public void clear(ECSEntity entity) {
        Collection<Component> toRemove = new ArrayList<>(this.entityComponents.values());
        entityComponents.clear();
        for (Component component : toRemove) {
            removeComponent(component);
        }
    }

    @Override
    public String toString() {
        return "LocalMapHandle{\n" +
                "entityComponents=" + entityComponents +
                ",\n worldComponentStorage=" + worldComponentStorage +
                "\n}";
    }
}
