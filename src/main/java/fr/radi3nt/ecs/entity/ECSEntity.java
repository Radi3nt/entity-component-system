package fr.radi3nt.ecs.entity;

import fr.radi3nt.ecs.components.Component;

import java.util.Optional;

public interface ECSEntity {

    void addComponent(Component component);
    void removeComponent(Class<?> tClass);

    <T extends Component> Optional<T> getComponent(Class<T> tClass);

    void setEnabled(boolean enabled);
    boolean isEnabled();

    void destroy();
}
