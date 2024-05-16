package fr.radi3nt.ecs.world.handle;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.world.listener.ComponentListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WorldComponentStorage {

    private final Map<Class<? extends Component>, Collection<Component>> allComponents = new ConcurrentHashMap<>();
    private final Map<Class<? extends Component>, Collection<ComponentListener>> listeners = new ConcurrentHashMap<>();

    public void addComponent(Component component) {
        Collection<Component> allComponentOfThatType = getComponentCollection(component.getClass());
        allComponentOfThatType.add(component);

        notifyAddListeners(component);
    }

    private void notifyAddListeners(Component component) {
        Collection<ComponentListener> componentListeners = listeners.get(component.getClass());
        if (componentListeners==null)
            return;
        for (ComponentListener componentListener : componentListeners) {
            componentListener.added(component);
        }
    }

    public void removeComponent(Component component) {
        Collection<Component> components = allComponents.get(component.getClass());
        if (components!=null) {
            components.remove(component);
        }

        notifyRemoveListeners(component);
    }

    private void notifyRemoveListeners(Component component) {
        Collection<ComponentListener> componentListeners = listeners.get(component.getClass());
        if (componentListeners==null)
            return;
        for (ComponentListener componentListener : componentListeners) {
            componentListener.removed(component);
        }
    }

    public Collection<Component> getComponentCollection(Class<? extends Component> componentClass) {
        return allComponents.computeIfAbsent(componentClass, aClass -> new ArrayList<>());
    }

    public Map<Class<? extends Component>, Collection<Component>> getAllComponents() {
        return allComponents;
    }

    public Map<Class<? extends Component>, Collection<ComponentListener>> getListeners() {
        return listeners;
    }
}
