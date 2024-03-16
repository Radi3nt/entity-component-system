package fr.radi3nt.ecs.system;

import fr.radi3nt.ecs.components.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public abstract class TypeComponentSystem<T extends Component> implements TypedComponentSystem<T> {

    protected final Collection<T> components = ConcurrentHashMap.newKeySet();
    protected final Class<T> type;

    protected TypeComponentSystem(Class<T> type) {
        this.type = type;
    }


    @Override
    public void add(Component comp) {
        if (type.isAssignableFrom(comp.getClass())) {
            addComponent((T) comp);
        }
    }

    public void addComponent(T comp) {
        components.add(comp);
    }

    @Override
    public void remove(Component comp) {
        if (type.isAssignableFrom(comp.getClass())) {
            removeComponent((T) comp);
        }
    }

    public void removeComponent(T comp) {
        components.remove(comp);
    }
}
