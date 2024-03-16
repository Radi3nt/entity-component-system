package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.components.EntityComponent;

public class HealthComponent extends EntityComponent {

    public float health;

    public HealthComponent(float health) {
        this.health = health;
    }

    public HealthComponent() {
    }
}
