package fr.radi3nt.ecs.system;

import fr.radi3nt.ecs.world.ECSWorld;

public abstract class ECSWorldComponentSystem implements ComponentSystem {

    protected final ECSWorld world;

    public ECSWorldComponentSystem(ECSWorld world) {
        this.world = world;
    }
}
