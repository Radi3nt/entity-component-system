package fr.radi3nt.ecs.system;

import fr.radi3nt.ecs.components.Component;

public interface ComponentSystem {

    void add(Component comp);
    void remove(Component comp);

}
