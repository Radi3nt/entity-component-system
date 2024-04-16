package fr.radi3nt.ecs.entity;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.entity.world.ECSWorld;
import fr.radi3nt.ecs.system.ComponentMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ECSObjectEntity implements ECSEntity {

    private final Map<Class<?>, Component> components = new ConcurrentHashMap<>();
    private ComponentMapper componentMapper;

    public ECSObjectEntity() {

    }

    @Override
    public <T extends Component> T getComponent(Class<T> tClass) {
        return (T) components.get(tClass);
    }

    @Override
    public void setWorld(ECSWorld world) {
        componentMapper = world.getComponentMapper();
        for (Component value : components.values()) {
            actuallyAdd(value);
        }
    }

    @Override
    public void removeFromWorld() {
        for (Component value : components.values()) {
            actuallyRemove(value);
        }
        componentMapper = null;
    }

    @Override
    public void addComponent(Component component) {
        Component old = components.put(component.getClass(), component);
        if (componentMapper!=null) {
            if (old != null)
                actuallyRemove(old);
            actuallyAdd(component);
        }
    }

    private void actuallyAdd(Component component) {
        component.add(this);
        componentMapper.add(component);
    }

    @Override
    public void removeComponent(Class<?> tClass) {
        Component old = components.remove(tClass);
        if (old != null) {
            removeComponent(old);
        }
    }

    private void removeComponent(Component old) {
        if (componentMapper!=null) {
            actuallyRemove(old);
        }
    }

    private void actuallyRemove(Component old) {
        componentMapper.remove(old);
        old.remove();
    }

}