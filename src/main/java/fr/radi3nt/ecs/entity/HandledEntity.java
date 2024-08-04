package fr.radi3nt.ecs.entity;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.world.handle.ComponentWorldHandle;

import java.util.Collection;
import java.util.function.Consumer;

public class HandledEntity implements ECSEntity {

    private final ComponentWorldHandle worldHandle;
    private boolean destroyed = false;

    public HandledEntity(ComponentWorldHandle worldHandle) {
        this.worldHandle = worldHandle;
    }

    public HandledEntity(ComponentWorldHandle worldHandle, Component... components) {
        this.worldHandle = worldHandle;
        worldHandle.add(this, components);
    }


    public HandledEntity(ComponentWorldHandle worldHandle, Collection<? extends Component> components) {
        this.worldHandle = worldHandle;
        worldHandle.add(this, components);
    }

    @Override
    public <T extends Component> T getComponent(Class<T> tClass) {
        if (destroyed)
            throw new IllegalStateException("Entity has already been destroyed");
        return worldHandle.get(this, tClass);
    }

    @Override
    public <T extends Component> void consume(Class<T> tClass, Consumer<T> consumer) {
        T component = getComponent(tClass);
        if (component==null)
            return;
        consumer.accept(component);
    }

    @Override
    public Collection<Component> getComponents() {
        if (destroyed)
            throw new IllegalStateException("Entity has already been destroyed");
        return worldHandle.get(this);
    }

    @Override
    public void addComponent(Component component) {
        if (destroyed)
            throw new IllegalStateException("Entity has already been destroyed");
        worldHandle.add(this, component);
    }

    @Override
    public void removeComponent(Class<? extends Component> tClass) {
        if (destroyed)
            throw new IllegalStateException("Entity has already been destroyed");
        worldHandle.remove(this, tClass);
    }

    @Override
    public void destroy() {
        if (!destroyed)
            worldHandle.clear(this);
        destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public String toString() {
        return "HandledEntity{\n" +
                "worldHandle=" + worldHandle +
                ",\n destroyed=" + destroyed +
                "\n}";
    }
}