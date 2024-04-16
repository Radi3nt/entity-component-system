package fr.radi3nt.ecs.main.components;

import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.system.TypeComponentSystem;

public class DeathOnHealthReachesZeroSystem extends TypeComponentSystem<DeathOnHealthReachesZeroComponent> {

    public static final DeathOnHealthReachesZeroSystem INSTANCE = new DeathOnHealthReachesZeroSystem();

    private DeathOnHealthReachesZeroSystem() {
        super(DeathOnHealthReachesZeroComponent.class);
    }

    public void update() {
        for (DeathOnHealthReachesZeroComponent component : INSTANCE.components) {
            if (component.isEnabled()) {
                ECSEntity current = component.current;
                HealthComponent healthComponent = current.getComponent(HealthComponent.class);
                if (healthComponent.health<=0)
                    current.getComponent(DeathStateComponent.class).dead = true;
            }
        }
    }
}
