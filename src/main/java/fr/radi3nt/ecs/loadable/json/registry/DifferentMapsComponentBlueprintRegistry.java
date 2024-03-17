package fr.radi3nt.ecs.loadable.json.registry;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.loadable.json.parser.PersistentComponentParserRegistry;
import fr.radi3nt.ecs.loadable.persistence.loader.PersistentComponentLoaderRegistry;
import fr.radi3nt.ecs.loadable.registry.ComponentRegistry;

public class DifferentMapsComponentBlueprintRegistry implements ComponentBlueprintRegistry {

    private final ComponentRegistry componentRegistry;
    private final PersistentComponentParserRegistry parserRegistry;
    private final PersistentComponentLoaderRegistry loaderRegistry;

    public DifferentMapsComponentBlueprintRegistry(ComponentRegistry componentRegistry, PersistentComponentParserRegistry parserRegistry, PersistentComponentLoaderRegistry loaderRegistry) {
        this.componentRegistry = componentRegistry;
        this.parserRegistry = parserRegistry;
        this.loaderRegistry = loaderRegistry;
    }

    @Override
    public ComponentPersistenceType get(String name) {
        Class<? extends Component> aClass = componentRegistry.getComponentClassById(name);
        return new ComponentPersistenceType(loaderRegistry.getLoaderFromClass(aClass), parserRegistry.getParserFromClass(aClass));
    }
}
