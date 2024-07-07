package fr.radi3nt.ecs.loadable.json.parser.values.parsers;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.json.JsonArray;
import fr.radi3nt.json.JsonValue;

import java.lang.reflect.Array;

public class ArrayJsonValueParser implements JsonValueParser {

    private final Class<?> objectClass;
    private final JsonValueParser objectParser;

    public ArrayJsonValueParser(Class<?> objectClass, JsonValueParser objectParser) {
        this.objectClass = objectClass;
        this.objectParser = objectParser;
    }

    @Override
    public Object parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException {
        if (value==null || !value.isArray())
            throw JsonComponentParseException.shouldArray(path);

        JsonArray array = value.asArray();

        Object objectArray = Array.newInstance(objectClass, array.size());
        for (int i = 0; i < array.size(); i++) {
            Array.set(objectArray, i, new EncapsulatingVariableJsonValueParser(objectParser).parseCustom(array.get(i), path + "[" + i + "]", storage));
        }
        return objectArray;
    }
}
