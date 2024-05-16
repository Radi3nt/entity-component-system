package fr.radi3nt.ecs.world.iterator;

import fr.radi3nt.ecs.components.Component;

import java.util.Iterator;

public interface ComponentIteratorProvider<T extends Component> {

    Iterator<T> getIterator();

}
