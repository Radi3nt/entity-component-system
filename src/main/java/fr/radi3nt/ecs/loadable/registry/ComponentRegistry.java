package fr.radi3nt.ecs.loadable.registry;

import fr.radi3nt.ecs.components.Component;

public interface ComponentRegistry {

    Class<? extends Component> getComponentClassById(String id);

}
