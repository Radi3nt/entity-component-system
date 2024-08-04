package fr.radi3nt.ecs.loadable.persistence.loader;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.*;
import fr.radi3nt.ecs.persistence.data.MappedPersistentData;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationReflectionPersistentComponentLoader extends MappedPersistentComponentLoader implements PersistentComponentLoader {

    private final Class<? extends Component> componentClass;

    public AnnotationReflectionPersistentComponentLoader(Class<? extends Component> componentClass) {
        this.componentClass = componentClass;
    }

    @Override
    protected Component loadInternal(MappedPersistentData mappedPersistentData) throws ComponentPersistenceException {
        try {
            Collection<String> alreadyUsed = new ArrayList<>();
            Component component = createComponent(mappedPersistentData, alreadyUsed);

            for (Field field : componentClass.getFields()) {
                ComponentFieldPersistent componentPersistent = field.getAnnotation(ComponentFieldPersistent.class);
                if (componentPersistent==null)
                    continue;
                String[] id = componentPersistent.ids();
                Object o = null;

                if (id.length==0) {
                    o = possiblyGet(mappedPersistentData, alreadyUsed, field.getName());
                } else {
                    for (String s : id) {
                        o = possiblyGet(mappedPersistentData, alreadyUsed, s);
                        if (o!=null) {
                            break;
                        }
                    }
                }


                if (o == null)
                    continue;

                field.setAccessible(true);
                field.set(component, o);
            }

            return component;
        } catch (Exception e) {
            throw new ComponentPersistenceException("Failed to load component '" + componentClass.getSimpleName() + "' using reflection", e);
        }
    }

    private static Object possiblyGet(MappedPersistentData mappedPersistentData, Collection<String> alreadyUsed, String name) {
        return alreadyUsed.contains(name) ? null : mappedPersistentData.get(name);
    }

    private Component createComponent(MappedPersistentData mappedPersistentData, Collection<String> alreadyUsed) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Executable> constructors = new ArrayList<>(Arrays.asList(componentClass.getDeclaredConstructors()));
        constructors.addAll(Arrays.asList(componentClass.getDeclaredMethods()).stream().filter(method -> Modifier.isStatic(method.getModifiers()) && method.getReturnType().isAssignableFrom(componentClass)).collect(Collectors.toList()));

        constructors.sort((o1, o2) -> Integer.compare(o2.getParameterCount(), o1.getParameterCount()));
        for (Executable executable : constructors) {
            if (executable.isAnnotationPresent(ComponentBuilderPersistent.class)) {
                Collection<Object> parameterArguments = new ArrayList<>();
                Collection<String> currentAlreadyUsed = new ArrayList<>();
                if (!searchParameters(mappedPersistentData, executable, parameterArguments, currentAlreadyUsed))
                    continue;

                alreadyUsed.addAll(currentAlreadyUsed);

                if (executable instanceof Constructor<?>)
                    return (Component) ((Constructor<?>) executable).newInstance(parameterArguments.toArray(new Object[0]));
                if (executable instanceof Method)
                    return (Component) ((Method) executable).invoke(null, parameterArguments.toArray(new Object[]{0}));
            }
        }

        throw new UnsupportedOperationException("Could not create component: no constructor available in '" + componentClass.getSimpleName() + "' matching the required conditions");
    }

    private static boolean searchParameters(MappedPersistentData mappedPersistentData, Executable declaredConstructor, Collection<Object> parameterArguments, Collection<String> currentAlreadyUsed) {
        for (Parameter parameter : declaredConstructor.getParameters()) {
            String[] ids = getIds(parameter);
            boolean found = false;
            for (String id : ids) {
                Object o = mappedPersistentData.get(id);
                if (o==null)
                    continue;

                parameterArguments.add(o);
                currentAlreadyUsed.add(id);
                found = true;
                break;
            }
            if (!found)
                return false;
        }

        return true;
    }

    private static String[] getIds(Parameter parameter)  {
        ComponentParameterFieldPersistent fieldPersistent = parameter.getAnnotation(ComponentParameterFieldPersistent.class);
        if (fieldPersistent!=null) {
            Field declaredField;
            try {
                declaredField = parameter.getDeclaringExecutable().getDeclaringClass().getDeclaredField(fieldPersistent.fieldName());
            } catch (NoSuchFieldException e) {
                throw new UnsupportedOperationException("Component builder is invalid");
            }
            ComponentFieldPersistent annotation = declaredField.getAnnotation(ComponentFieldPersistent.class);
            if (annotation==null)
                throw new UnsupportedOperationException("Component builder is invalid");
            String[] ids = annotation.ids();
            if (ids.length==0)
                return new String[]{declaredField.getName()};
            return ids;
        }

        ComponentParameterPersistent annotation = parameter.getAnnotation(ComponentParameterPersistent.class);
        if (annotation==null)
            throw new UnsupportedOperationException("Component builder is invalid");
        return annotation.ids();
    }

    @Override
    public Class<? extends Component> getLoadedClass() {
        return componentClass;
    }
}
