package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.components.EntityComponent;
import fr.radi3nt.ecs.main.DynamicsConstants;

public class MovementComponent extends EntityComponent {

    public DynamicsConstants constants;
    public DynamicsConstants properties;

    public MovementComponent(DynamicsConstants constants) {
        this.constants = constants;
    }

    public MovementComponent() {

    }
}
