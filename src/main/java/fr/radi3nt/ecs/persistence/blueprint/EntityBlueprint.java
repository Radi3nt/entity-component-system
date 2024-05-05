package fr.radi3nt.ecs.persistence.blueprint;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.persistence.blueprint.comp.ComponentBlueprint;
import fr.radi3nt.ecs.world.ECSEntityProvider;

import java.util.ArrayList;
import java.util.Collection;

public class EntityBlueprint {

    private final Collection<ComponentBlueprint> blueprints;

    public EntityBlueprint(Collection<ComponentBlueprint> blueprints) {
        this.blueprints = blueprints;
    }

    public ECSEntity create(ECSEntityProvider provider) {
        Collection<Component> components = createComponents();
        return provider.create(components);
    }

    private Collection<Component> createComponents() {
        Collection<Component> components = new ArrayList<>();

        for (ComponentBlueprint blueprint : blueprints) {
            components.add(blueprint.create());
        }

        return components;
    }
}
