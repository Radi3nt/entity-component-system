package fr.radi3nt.ecs.loadable.json.parser.values;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.ArrayJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.EncapsulatingVariableJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.EnumJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.JsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.registry.ClassJsonValueParserRegistry;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentBuilderPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentFieldPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentParameterFieldPersistent;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentParameterPersistent;
import fr.radi3nt.json.JsonValue;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JsonCustomValuesParser {

    private final Map<String, JsonValueParser> pathsAndClasses;

    public JsonCustomValuesParser(Map<String, JsonValueParser> pathsAndClasses) {
        this.pathsAndClasses = pathsAndClasses;
    }

    public static Map<String, JsonValueParser> fromFieldsAndBuilders(ClassJsonValueParserRegistry registry, Class<?> aClass) {
        Map<String, JsonValueParser> variables = new HashMap<>();

        for (Constructor<?> declaredConstructor : aClass.getDeclaredConstructors()) {
            if (!declaredConstructor.isAnnotationPresent(ComponentBuilderPersistent.class)) {
                continue;
            }
            for (Parameter parameter : declaredConstructor.getParameters()) {
                String[] names = getNames(parameter, "Could not find ComponentParameterPersistent annotation on parameter " + parameter.getName() + " in constructor of class " + aClass.getName() + ", unable to create parsers", "Field for parameter " + parameter.getName() + " in constructor of class " + aClass.getName() + " isn't annotated with ComponentFieldPersistent");
                addToVariables(names, variables, parserForClass(registry, parameter.getType()));
            }
        }

        for (Method declaredMethod : aClass.getDeclaredMethods()) {
            if (!Modifier.isStatic(declaredMethod.getModifiers()) || !declaredMethod.isAnnotationPresent(ComponentBuilderPersistent.class) || declaredMethod.getReturnType().isAssignableFrom(aClass)) {
                continue;
            }

            for (Parameter parameter : declaredMethod.getParameters()) {
                String[] names = getNames(parameter, "Could not find ComponentParameterPersistent annotation on parameter " + parameter.getName() + " in method " + declaredMethod.getName() + " of class " + aClass.getName() + ", unable to create parsers", "Field for parameter " + parameter.getName() + " in method " + declaredMethod.getName() + " of class " + aClass.getName() + " isn't annotated with ComponentFieldPersistent");
                addToVariables(names, variables, parserForClass(registry, parameter.getType()));
            }
        }

        for (Field declaredField : aClass.getFields()) {
            if (!declaredField.isAnnotationPresent(ComponentFieldPersistent.class)) {
                continue;
            }

            Class<?> currentClass = declaredField.getType();

            ComponentFieldPersistent componentFieldPersistent = declaredField.getAnnotation(ComponentFieldPersistent.class);
            if (componentFieldPersistent.ids().length==0) {
                variables.put(declaredField.getName(), parserForClass(registry, currentClass));
            } else {
                addToVariables(componentFieldPersistent.ids(), variables, parserForClass(registry, currentClass));
            }
        }
        return variables;
    }

    private static String[] getNames(Parameter parameter, String error1, String error2) {
        ComponentParameterFieldPersistent fieldPersistent = parameter.getAnnotation(ComponentParameterFieldPersistent.class);
        if (fieldPersistent!=null) {
            Field declaredField;
            try {
                declaredField = parameter.getDeclaringExecutable().getDeclaringClass().getDeclaredField(fieldPersistent.fieldName());
            } catch (NoSuchFieldException e) {
                throw new UnsupportedOperationException(error2);
            }
            ComponentFieldPersistent annotation = declaredField.getAnnotation(ComponentFieldPersistent.class);
            if (annotation==null)
                throw new UnsupportedOperationException(error2);
            String[] ids = annotation.ids();
            if (ids.length==0)
                return new String[]{declaredField.getName()};
            return ids;
        }

        ComponentParameterPersistent annotation = parameter.getAnnotation(ComponentParameterPersistent.class);
        if (annotation==null)
            throw new UnsupportedOperationException(error1);
        return annotation.ids();
    }

    private static void addToVariables(String[] names, Map<String, JsonValueParser> variables, JsonValueParser registry) {
        for (String id : names) {
            variables.put(id, registry);
        }
    }

    private static JsonValueParser parserForClass(ClassJsonValueParserRegistry registry, Class<?> currentClass) {
        Optional<JsonValueParser> parser = registry.getParserFromClass(currentClass);
        if (parser.isPresent())
            return new EncapsulatingVariableJsonValueParser(parser.get());

        if (currentClass.isArray()) {
            Class<?> objectClass = currentClass.getComponentType();
            return new EncapsulatingVariableJsonValueParser(new ArrayJsonValueParser(objectClass, parserForClass(registry, objectClass)));
        }
        if (currentClass.isEnum())
            return new EncapsulatingVariableJsonValueParser(new EnumJsonValueParser((Class<Enum>) currentClass));

        throw new IllegalArgumentException("Could not find parser for class " + currentClass.getSimpleName());
    }

    public boolean isCustomValue(String path) {
        return pathsAndClasses.containsKey(path);
    }

    public Object parseCustom(JsonValue value, String path, VariableStorage variableStorage) throws JsonComponentParseException {
        JsonValueParser parser = pathsAndClasses.get(path);
        return parser.parseCustom(value, path, variableStorage);
    }

}
