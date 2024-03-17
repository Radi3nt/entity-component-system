package fr.radi3nt.ecs.entity.provider;

import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.entity.ECSObjectEntity;
import fr.radi3nt.ecs.system.SystemHolder;

public class ObjectECSEntityProvider implements ECSEntityProvider {

    protected final SystemHolder systemHolder;

    public ObjectECSEntityProvider(SystemHolder systemHolder) {
        this.systemHolder = systemHolder;
    }

    @Override
    public ECSEntity entity() {
        return new ECSObjectEntity(systemHolder);
    }
}
