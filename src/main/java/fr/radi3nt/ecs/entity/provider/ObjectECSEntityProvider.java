package fr.radi3nt.ecs.entity.provider;

import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.entity.ECSObjectEntity;
import fr.radi3nt.ecs.system.registerer.GlobalComponentSystemRegisterer;

public class ObjectECSEntityProvider implements ECSEntityProvider {

    protected final GlobalComponentSystemRegisterer registerer;

    public ObjectECSEntityProvider(GlobalComponentSystemRegisterer registerer) {
        this.registerer = registerer;
    }

    @Override
    public ECSEntity entity() {
        return new ECSObjectEntity(registerer);
    }
}
