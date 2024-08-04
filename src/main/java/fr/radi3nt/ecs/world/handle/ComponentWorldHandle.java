package fr.radi3nt.ecs.world.handle;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.entity.HandledEntity;

import java.util.Collection;

public interface ComponentWorldHandle {

    void add(ECSEntity entity, Component component);
    void add(ECSEntity entity, Component... component);
    void add(ECSEntity entity, Collection<? extends Component> components);
    void remove(ECSEntity entity, Component component);
    void remove(ECSEntity entity, Class<? extends Component> component);
    <T extends Component> T get(ECSEntity entity, Class<T> componentClass);
    Collection<Component> get(HandledEntity handledEntity);

    void clear(ECSEntity entity);

}
