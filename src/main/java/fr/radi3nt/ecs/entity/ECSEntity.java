package fr.radi3nt.ecs.entity;

import fr.radi3nt.ecs.components.Component;

public interface ECSEntity {

    void addComponent(Component component);
    void removeComponent(Class<? extends Component> tClass);

    void destroy();
    boolean isDestroyed();

    <T extends Component> T getComponent(Class<T> tClass);
}
