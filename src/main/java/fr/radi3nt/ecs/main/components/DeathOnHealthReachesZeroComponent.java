package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.components.EnableableComponent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentBuilderPersistent;

public class DeathOnHealthReachesZeroComponent extends EnableableComponent {

    @ComponentBuilderPersistent
    public DeathOnHealthReachesZeroComponent() {
        super();
    }
}
