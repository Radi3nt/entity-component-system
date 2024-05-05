package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.world.ECSWorld;

import java.util.Iterator;

public class DeathOnHealthReachesZeroSystem {

    private final ECSWorld world;

    public DeathOnHealthReachesZeroSystem(ECSWorld world) {
        this.world = world;
    }

    public void update() {
        for (Iterator<DeathOnHealthReachesZeroComponent> it = world.getComponentIterator(DeathOnHealthReachesZeroComponent.class); it.hasNext(); ) {
            DeathOnHealthReachesZeroComponent component = it.next();
            if (component.isEnabled()) {
                ECSEntity current = component.current;
                HealthComponent healthComponent = current.getComponent(HealthComponent.class);
                if (healthComponent.health<=0)
                    current.getComponent(DeathStateComponent.class).dead = true;
            }
        }
    }
}
