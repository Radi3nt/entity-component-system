package fr.radi3nt.ecs.entity.world;

import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.system.ComponentMapper;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ListingECSWorld implements ECSWorld {

    private final Set<ECSEntity> ecsEntities = ConcurrentHashMap.newKeySet();
    private final ComponentMapper componentMapper;

    public ListingECSWorld(ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    @Override
    public void addEntity(ECSEntity entity) {
        boolean add = ecsEntities.add(entity);
        if (add) {
            entity.setWorld(this);
        }
    }

    @Override
    public void removeEntity(ECSEntity entity) {
        boolean remove = ecsEntities.remove(entity);
        if (remove) {
            entity.removeFromWorld();
        }
    }

    @Override
    public ComponentMapper getComponentMapper() {
        return componentMapper;
    }
}
