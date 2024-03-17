package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.components.EntityComponent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentConstructorPersistent;

public class DeathOnHealthReachesZeroComponent extends EntityComponent {

    @ComponentConstructorPersistent
    public DeathOnHealthReachesZeroComponent() {
        super();
    }
}
