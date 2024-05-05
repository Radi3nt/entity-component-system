package fr.radi3nt.ecs.world.listener;

import fr.radi3nt.ecs.components.Component;

public interface ComponentListener {

    void added(Component component);
    void removed(Component component);

}
