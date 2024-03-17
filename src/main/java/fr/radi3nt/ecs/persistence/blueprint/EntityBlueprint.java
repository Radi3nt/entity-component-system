package fr.radi3nt.ecs.persistence.blueprint;

import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.entity.provider.ECSEntityProvider;
import fr.radi3nt.ecs.persistence.blueprint.comp.ComponentBlueprint;

import java.util.*;

public class EntityBlueprint {

    private final Collection<ComponentBlueprint> blueprints;

    public EntityBlueprint(Collection<ComponentBlueprint> blueprints) {
        this.blueprints = blueprints;
    }

    public ECSEntity create(ECSEntityProvider provider) {
        ECSEntity entity = provider.entity();
        return create(entity);
    }

    private ECSEntity create(ECSEntity entity) {
        for (ComponentBlueprint blueprint : blueprints) {
            entity.addComponent(blueprint.create());
        }

        return entity;
    }

}
