package fr.radi3nt.ecs.loadable.persistence.loader;

import fr.radi3nt.ecs.components.Component;

import java.util.Map;

public class MapPersistentComponentLoaderRegistry implements PersistentComponentLoaderRegistry {

    private final Map<Class<? extends Component>, PersistentComponentLoader> loaderMap;

    public MapPersistentComponentLoaderRegistry(Map<Class<? extends Component>, PersistentComponentLoader> loaderMap) {
        this.loaderMap = loaderMap;
    }

    @Override
    public PersistentComponentLoader getLoaderFromClass(Class<? extends Component> componentType) {
        return loaderMap.getOrDefault(componentType, new AnnotationReflectionPersistentComponentLoader(componentType));
    }
}
