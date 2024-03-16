package fr.radi3nt.ecs.loadable.registry;

import fr.radi3nt.ecs.components.Component;

import java.util.Map;

public class MapComponentRegistry implements ComponentRegistry {

    private final Map<String, Class<? extends Component>> componentIds;

    public MapComponentRegistry(Map<String, Class<? extends Component>> componentIds) {
        this.componentIds = componentIds;
    }

    @Override
    public Class<? extends Component> getComponentClassById(String id) {
        return componentIds.get(id);
    }
}
