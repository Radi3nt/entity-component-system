package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.components.EntityComponent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentBuilderPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentFieldPersistent;

public class DeathStateComponent extends EntityComponent {

    @ComponentFieldPersistent
    public boolean dead;

    @ComponentBuilderPersistent
    public DeathStateComponent() {
    }
}
