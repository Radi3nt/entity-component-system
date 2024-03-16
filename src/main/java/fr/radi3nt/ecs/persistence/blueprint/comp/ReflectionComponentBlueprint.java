package fr.radi3nt.ecs.persistence.blueprint.comp;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.persistence.blueprint.comp.ComponentBlueprint;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionComponentBlueprint<T extends Component> implements ComponentBlueprint {

    private final Class<T> componentClass;

    public ReflectionComponentBlueprint(Class<T> componentClass) {
        this.componentClass = componentClass;
    }

    @Override
    public T create() {
        try {
            Constructor<T> constructor = componentClass.getConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Could not create component " + componentClass.getSimpleName() + " from reflection, is there an empty constructor in the component class? Or maybe you should you a more specific factory that supports this component",  e);
        }
    }

    @Override
    public Class<T> getComponentType() {
        return componentClass;
    }
}
