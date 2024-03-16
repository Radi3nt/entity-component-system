package fr.radi3nt.ecs.system.registerer;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.system.ComponentSystem;

import java.util.Collection;

public class ListedGlobalComponentSystemRegisterer implements GlobalComponentSystemRegisterer {

    protected final Collection<ComponentSystem> systems;

    public ListedGlobalComponentSystemRegisterer(Collection<ComponentSystem> systems) {
        this.systems = systems;
    }

    @Override
    public void register(Component component) {
        for (ComponentSystem system : systems) {
            system.add(component);
        }
    }

    @Override
    public void unregister(Component component) {
        for (ComponentSystem system : systems) {
            system.remove(component);
        }
    }
}
