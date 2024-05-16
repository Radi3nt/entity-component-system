package fr.radi3nt.ecs.world;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.entity.HandledEntity;
import fr.radi3nt.ecs.world.handle.LocalMapHandle;
import fr.radi3nt.ecs.world.handle.WorldComponentStorage;
import fr.radi3nt.ecs.world.iterator.CollectionIteratorProvider;
import fr.radi3nt.ecs.world.iterator.ComponentIteratorProvider;
import fr.radi3nt.ecs.world.listener.ComponentListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ListingECSWorld implements ECSWorld {

    private final WorldComponentStorage worldComponentStorage = new WorldComponentStorage();

    @Override
    public ECSEntity create(Component... components) {
        HandledEntity handledEntity = new HandledEntity(new LocalMapHandle(worldComponentStorage), components);
        return handledEntity;
    }

    @Override
    public ECSEntity create(Collection<? extends Component> components) {
        HandledEntity handledEntity = new HandledEntity(new LocalMapHandle(worldComponentStorage), components);
        return handledEntity;
    }

    @Override
    public void destroy(ECSEntity entity) {
        entity.destroy();
    }

    @Override
    public <T extends Component> Iterator<T> getComponentIterator(Class<T> componentClass) {
        Collection<T> componentCollection = (Collection<T>) worldComponentStorage.getAllComponents().get(componentClass);
        if (componentCollection==null)
            return Collections.emptyIterator();
        return new ArrayList<>(componentCollection).iterator();
    }

    @Override
    public <T extends Component> ComponentIteratorProvider<T> getComponentProvider(Class<T> componentClass) {
        Collection<T> componentCollection = (Collection<T>) worldComponentStorage.getComponentCollection(componentClass);
        return new CollectionIteratorProvider<>(componentCollection);
    }

    @Override
    public void addListener(Class<? extends Component> componentClass, ComponentListener listener) {
        Collection<ComponentListener> componentListeners = worldComponentStorage.getListeners().computeIfAbsent(componentClass, aClass -> ConcurrentHashMap.newKeySet());
        componentListeners.add(listener);
    }

    @Override
    public void removeListener(Class<? extends Component> componentClass, ComponentListener listener) {
        Collection<ComponentListener> componentListeners = worldComponentStorage.getListeners().get(componentClass);
        if (componentListeners!=null)
            componentListeners.remove(listener);
    }
}
