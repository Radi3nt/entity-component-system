package fr.radi3nt.ecs.loadable.json.parser.values;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.ArrayJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.EncapsulatingVariableJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.EnumJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.JsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.registry.ClassJsonValueParserRegistry;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;
import fr.radi3nt.json.JsonValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JsonCustomValuesParser implements JsonValuesParser {

    private final Map<String, JsonValueParser> pathsAndClasses;

    public JsonCustomValuesParser(Map<String, JsonValueParser> pathsAndClasses) {
        this.pathsAndClasses = pathsAndClasses;
    }

    public static Map<String, JsonValueParser> fromFields(ClassJsonValueParserRegistry registry, Class<?> aClass) {
        Map<String, JsonValueParser> variables = new HashMap<>();
        for (Field declaredField : aClass.getFields()) {
            Class<?> currentClass = declaredField.getType();
            variables.put(declaredField.getName(), parserForClass(registry, currentClass));
        }
        return variables;
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

        return null;
    }

    @Override
    public boolean isCustomValue(JsonValue value, String path) {
        return pathsAndClasses.containsKey(path);
    }

    @Override
    public Object parseCustom(JsonValue value, String path, VariableStorage variableStorage) throws JsonComponentParseException {
        JsonValueParser parser = pathsAndClasses.get(path);
        return parser.parseCustom(value, path, variableStorage);
    }

}
