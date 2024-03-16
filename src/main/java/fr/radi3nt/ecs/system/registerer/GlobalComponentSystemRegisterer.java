package fr.radi3nt.ecs.system.registerer;

import fr.radi3nt.ecs.components.Component;

public interface GlobalComponentSystemRegisterer {

    void register(Component component);
    void unregister(Component component);

}
