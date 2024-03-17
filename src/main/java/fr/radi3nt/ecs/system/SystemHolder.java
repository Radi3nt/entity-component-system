package fr.radi3nt.ecs.system;

import fr.radi3nt.ecs.components.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SystemHolder {

    protected final Collection<ComponentSystem> systems;

    public SystemHolder(Collection<ComponentSystem> systems) {
        this.systems = systems;
    }

    public SystemHolder(ComponentSystem... systems) {
        this(new ArrayList<>(Arrays.asList(systems)));
    }

    public void add(Component component) {
        for (ComponentSystem system : systems) {
            system.add(component);
        }
    }


    public void remove(Component component) {
        for (ComponentSystem system : systems) {
            system.remove(component);
        }
    }
}
