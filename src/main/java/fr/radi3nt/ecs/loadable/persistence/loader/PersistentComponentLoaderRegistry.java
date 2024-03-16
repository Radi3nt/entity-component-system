package fr.radi3nt.ecs.loadable.persistence.loader;

import fr.radi3nt.ecs.components.Component;

public interface PersistentComponentLoaderRegistry {

    PersistentComponentLoader getLoaderFromClass(Class<? extends Component> componentType);

}
