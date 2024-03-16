package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.components.SystemsComponent;

public class DeathOnHealthReachesZeroComponent extends SystemsComponent {

    public DeathOnHealthReachesZeroComponent() {
        super(DeathOnHealthReachesZeroSystem.INSTANCE);
    }
}
