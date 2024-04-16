package fr.radi3nt.ecs.entity.world;

import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.system.ComponentMapper;

public interface ECSWorld {

    void addEntity(ECSEntity entity);
    void removeEntity(ECSEntity entity);

    ComponentMapper getComponentMapper();

}
