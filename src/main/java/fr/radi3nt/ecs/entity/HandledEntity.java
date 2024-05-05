package fr.radi3nt.ecs.entity;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.world.handle.ComponentWorldHandle;

import java.util.Collection;

public class HandledEntity implements ECSEntity {

    private final ComponentWorldHandle world;
    private boolean destroyed = false;

    public HandledEntity(ComponentWorldHandle world) {
        this.world = world;
    }

    public HandledEntity(ComponentWorldHandle world, Component... components) {
        this.world = world;
        world.add(this, components);
    }


    public HandledEntity(ComponentWorldHandle world, Collection<? extends Component> components) {
        this.world = world;
        world.add(this, components);
    }

    @Override
    public <T extends Component> T getComponent(Class<T> tClass) {
        if (destroyed)
            throw new IllegalStateException("Entity has already been destroyed");
        return world.get(this, tClass);
    }

    @Override
    public void addComponent(Component component) {
        if (destroyed)
            throw new IllegalStateException("Entity has already been destroyed");
        world.add(this, component);
    }

    @Override
    public void removeComponent(Class<? extends Component> tClass) {
        if (destroyed)
            throw new IllegalStateException("Entity has already been destroyed");
        world.remove(this, tClass);
    }

    @Override
    public void destroy() {
        if (!destroyed)
            world.clear(this);
        destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}