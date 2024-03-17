package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.components.EntityComponent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentFieldPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentConstructorPersistent;

public class HealthComponent extends EntityComponent {

    @ComponentFieldPersistent
    public float health;

    @ComponentConstructorPersistent(fields = "health")
    public HealthComponent(float health) {
        this.health = health;
    }

    public HealthComponent() {
    }
}
