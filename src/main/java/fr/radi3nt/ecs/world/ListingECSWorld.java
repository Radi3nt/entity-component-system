package fr.radi3nt.ecs.world;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.entity.HandledEntity;
import fr.radi3nt.ecs.world.handle.LocalMapHandle;
import fr.radi3nt.ecs.world.handle.WorldComponentStorage;
import fr.radi3nt.ecs.world.listener.ComponentListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ListingECSWorld implements ECSWorld {

    private final Set<ECSEntity> ecsEntities = ConcurrentHashMap.newKeySet();
    private final WorldComponentStorage worldComponentStorage = new WorldComponentStorage();

    @Override
    public ECSEntity create(Component... components) {
        HandledEntity handledEntity = new HandledEntity(new LocalMapHandle(worldComponentStorage), components);
        ecsEntities.add(handledEntity);
        return handledEntity;
    }

    @Override
    public ECSEntity create(Collection<? extends Component> components) {
        HandledEntity handledEntity = new HandledEntity(new LocalMapHandle(worldComponentStorage), components);
        ecsEntities.add(handledEntity);
        return handledEntity;
    }

    @Override
    public void destroy(ECSEntity entity) {
        entity.destroy();
        ecsEntities.remove(entity);
    }

    @Override
    public <T extends Component> Iterator<T> getComponentIterator(Class<T> componentClass) {
        return (Iterator<T>) new ArrayList<>(worldComponentStorage.getAllComponents().get(componentClass)).iterator();
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
