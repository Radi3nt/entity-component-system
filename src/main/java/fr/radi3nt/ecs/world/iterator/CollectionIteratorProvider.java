package fr.radi3nt.ecs.world.iterator;

import fr.radi3nt.ecs.components.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class CollectionIteratorProvider<T extends Component> implements ComponentIteratorProvider<T> {

    private final Collection<T> components;

    public CollectionIteratorProvider(Collection<T> components) {
        this.components = components;
    }

    @Override
    public Iterator<T> getIterator() {
        return Collections.unmodifiableCollection(components).iterator();
    }
}
