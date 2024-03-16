package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.components.EntityComponent;

public class MaxHealthComponent extends EntityComponent {

    public float maxHealth;

    public MaxHealthComponent(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public MaxHealthComponent() {
    }
}
