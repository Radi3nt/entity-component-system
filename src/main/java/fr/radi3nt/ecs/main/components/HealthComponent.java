package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.components.EntityComponent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentBuilderPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentFieldPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentParameterFieldPersistent;

public class HealthComponent extends EntityComponent {

    @ComponentFieldPersistent
    public float health;

    @ComponentBuilderPersistent
    public HealthComponent(
            @ComponentParameterFieldPersistent(fieldName = "health")
            float health
    ) {
        this.health = health;
    }

    public HealthComponent() {
    }
}
