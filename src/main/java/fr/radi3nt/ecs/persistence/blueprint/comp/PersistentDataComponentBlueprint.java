package fr.radi3nt.ecs.persistence.blueprint.comp;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.loadable.persistence.loader.PersistentComponentLoader;
import fr.radi3nt.ecs.persistence.data.PersistentData;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;

public class PersistentDataComponentBlueprint implements ComponentBlueprint {

    public final Class<? extends Component> componentClass;
    public final PersistentComponentLoader loader;
    public final PersistentData data;

    public PersistentDataComponentBlueprint(Class<? extends Component> componentClass, PersistentComponentLoader loader, PersistentData data) {
        this.componentClass = componentClass;
        this.loader = loader;
        this.data = data;
    }


    @Override
    public Component create() {
        try {
            return loader.load(data);
        } catch (ComponentPersistenceException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<? extends Component> getComponentType() {
        return componentClass;
    }


}
