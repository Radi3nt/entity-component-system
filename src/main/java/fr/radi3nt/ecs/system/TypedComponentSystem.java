package fr.radi3nt.ecs.system;

import fr.radi3nt.ecs.components.Component;

public interface TypedComponentSystem<T extends Component> extends ComponentSystem {

    void addComponent(T comp);
    void removeComponent(T comp);

}
