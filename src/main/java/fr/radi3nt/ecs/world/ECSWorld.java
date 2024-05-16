package fr.radi3nt.ecs.world;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.world.iterator.ComponentIteratorProvider;
import fr.radi3nt.ecs.world.listener.ComponentListener;

import java.util.Iterator;

public interface ECSWorld extends ECSEntityProvider {

    void destroy(ECSEntity entity);

    <T extends Component> Iterator<T> getComponentIterator(Class<T> componentClass);
    <T extends Component> ComponentIteratorProvider<T> getComponentProvider(Class<T> componentClass);
    
    void addListener(Class<? extends Component> componentClass, ComponentListener listener);
    void removeListener(Class<? extends Component> componentClass, ComponentListener listener);
}
