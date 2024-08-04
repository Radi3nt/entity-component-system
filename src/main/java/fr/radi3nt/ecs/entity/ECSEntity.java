package fr.radi3nt.ecs.entity;

import fr.radi3nt.ecs.components.Component;

import java.util.Collection;
import java.util.function.Consumer;

public interface ECSEntity {

    void addComponent(Component component);
    void removeComponent(Class<? extends Component> tClass);

    void destroy();
    boolean isDestroyed();

    <T extends Component> T getComponent(Class<T> tClass);
    <T extends Component> void consume(Class<T> tClass, Consumer<T> consumer);

    Collection<Component> getComponents();
}
