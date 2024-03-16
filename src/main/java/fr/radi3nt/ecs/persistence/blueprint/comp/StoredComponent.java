package fr.radi3nt.ecs.persistence.blueprint.comp;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.loadable.persistence.loader.PersistentComponentLoader;
import fr.radi3nt.ecs.persistence.data.PersistentData;

public class StoredComponent {

    public final Class<? extends Component> componentClass;
    public final PersistentComponentLoader loader;
    public final PersistentData data;

    public StoredComponent(Class<? extends Component> componentClass, PersistentComponentLoader loader, PersistentData data) {
        this.componentClass = componentClass;
        this.loader = loader;
        this.data = data;
    }
}
