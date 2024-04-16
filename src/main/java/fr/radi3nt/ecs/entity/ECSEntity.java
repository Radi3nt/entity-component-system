package fr.radi3nt.ecs.entity;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.entity.world.ECSWorld;

public interface ECSEntity {

    void addComponent(Component component);
    void removeComponent(Class<?> tClass);

    <T extends Component> T getComponent(Class<T> tClass);

    void setWorld(ECSWorld world);
    void removeFromWorld();
}
