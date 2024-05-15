package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.components.EntityComponent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentBuilderPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentFieldPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentParameterFieldPersistent;

public class MaxHealthComponent extends EntityComponent {

    @ComponentFieldPersistent(ids = {"maxHealth", "max", "health"})
    public float maxHealth;

    @ComponentBuilderPersistent()
    public MaxHealthComponent(
            @ComponentParameterFieldPersistent(fieldName = "maxHealth")
            float maxHealth
    ) {
        this.maxHealth = maxHealth;
    }

    public MaxHealthComponent() {
    }
}
