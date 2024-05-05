package fr.radi3nt.ecs.world.listener;

import fr.radi3nt.ecs.components.Component;

public interface TypedComponentListener<T> extends ComponentListener {


    @Override
    default void added(Component component) {
        addComponent((T) component);
    }

    void addComponent(T component);

    @Override
    default void removed(Component component) {
        removeComponent((T) component);
    }

    void removeComponent(T component);

}
