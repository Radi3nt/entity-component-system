package fr.radi3nt.ecs.system;

import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.world.ECSWorld;

public class ECSEntityWorldComponentSystem extends ECSWorldComponentSystem {

    protected final ECSEntity worldEntity;

    public ECSEntityWorldComponentSystem(ECSWorld world, ECSEntity worldEntity) {
        super(world);
        this.worldEntity = worldEntity;
    }
}
