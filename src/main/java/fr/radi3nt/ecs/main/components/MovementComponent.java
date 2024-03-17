package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.components.EntityComponent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentConstructorPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentFieldPersistent;
import fr.radi3nt.ecs.main.DynamicsConstants;

public class MovementComponent extends EntityComponent {

    @ComponentFieldPersistent
    public DynamicsConstants constants;
    public DynamicsConstants properties;

    @ComponentConstructorPersistent(fields = "constants")
    public MovementComponent(DynamicsConstants constants) {
        this.constants = constants;
    }

    public MovementComponent() {

    }
}
