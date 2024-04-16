package fr.radi3nt.ecs.loadable.persistence.loader;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentConstructorPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentFieldPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.MappedPersistentComponentLoader;
import fr.radi3nt.ecs.persistence.data.MappedPersistentData;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotationReflectionPersistentComponentLoader extends MappedPersistentComponentLoader implements PersistentComponentLoader {

    private final Class<? extends Component> componentClass;

    public AnnotationReflectionPersistentComponentLoader(Class<? extends Component> componentClass) {
        this.componentClass = componentClass;
    }

    @Override
    protected Component loadInternal(MappedPersistentData mappedPersistentData) throws ComponentPersistenceException {
        try {
            Component component = createConstructor(mappedPersistentData);

            for (Field field : componentClass.getFields()) {
                if (field.isAnnotationPresent(ComponentFieldPersistent.class)) {
                    ComponentFieldPersistent componentPersistent = field.getAnnotation(ComponentFieldPersistent.class);
                    String[] id = componentPersistent.ids();
                    Object o = null;

                    if (id.length==0) {
                        o = mappedPersistentData.get(field.getName());
                    } else if (id.length==1) {
                        o = mappedPersistentData.get(id[0]);
                    } else {
                        for (String s : id) {
                            o = mappedPersistentData.get(s);
                            if (o!=null) {
                                break;
                            }
                        }
                    }


                    if (componentPersistent.required() && o == null)
                        throw new UnsupportedOperationException("Could not load component, since the required field '" + field.getName() + "' is not set");
                    field.setAccessible(true);
                    field.set(componentClass, o);
                }
            }

            return component;
        } catch (Exception e) {
            throw new ComponentPersistenceException("Failed to load component '" + componentClass.getSimpleName() + "' using reflection", e);
        }
    }

    private Component createConstructor(MappedPersistentData mappedPersistentData) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<Constructor<?>> constructors = new ArrayList<>(Arrays.asList(componentClass.getDeclaredConstructors()));
        constructors.sort((o1, o2) -> Integer.compare(o2.getParameterCount(), o1.getParameterCount()));
        for (Constructor<?> declaredConstructor : constructors) {
            if (declaredConstructor.isAnnotationPresent(ComponentConstructorPersistent.class)) {
                ComponentConstructorPersistent constructorPersistent = declaredConstructor.getAnnotation(ComponentConstructorPersistent.class);
                String[] fields = constructorPersistent.fields();
                Object[] initArgs = new Object[declaredConstructor.getParameterCount()];

                boolean failed = false;
                for (int i = 0, fieldsLength = fields.length; i < fieldsLength; i++) {
                    String field = fields[i];
                    Object o = findObjectWithName(mappedPersistentData, field);
                    if (o==null) {
                        failed = true;
                        break;
                    }
                    initArgs[i] = o;
                }
                if (failed)
                    continue;
                return (Component) declaredConstructor.newInstance(initArgs);
            }
        }

        throw new UnsupportedOperationException("Could not create component: no constructor available in '" + componentClass.getSimpleName() + "' matching the required conditions");
    }

    private Object findObjectWithName(MappedPersistentData persistentData, String fieldName) throws NoSuchFieldException {
        Field field = componentClass.getField(fieldName);
        if (field.isAnnotationPresent(ComponentFieldPersistent.class)) {
            ComponentFieldPersistent componentPersistent = field.getAnnotation(ComponentFieldPersistent.class);
            String[] ids = componentPersistent.ids();
            if (ids.length==0)
                return persistentData.get(fieldName);
            for (String id : ids) {
                Object o = persistentData.get(id);
                if (o!=null)
                    return o;
            }
        }
        return null;
    }
}
