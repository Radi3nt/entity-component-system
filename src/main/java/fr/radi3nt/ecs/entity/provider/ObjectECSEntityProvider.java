package fr.radi3nt.ecs.entity.provider;

import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.entity.ECSObjectEntity;

public class ObjectECSEntityProvider implements ECSEntityProvider {

    @Override
    public ECSEntity entity() {
        return new ECSObjectEntity();
    }
}
