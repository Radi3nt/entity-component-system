package fr.radi3nt.ecs.loadable.json.parser.values.parsers;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.json.JsonValue;

public class EnumJsonValueParser implements JsonValueParser {

    private final Class<? extends Enum> currentClassObject;

    public EnumJsonValueParser(Class<? extends Enum> currentClassObject) {
        this.currentClassObject = currentClassObject;
    }

    @Override
    public Object parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException {
        if (value==null || !value.isString()) {
            throw JsonComponentParseException.shouldValue(path, "a string representing an enum constant");
        }
        try {
            return Enum.valueOf(currentClassObject, value.asString().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new JsonComponentParseException("Failed to parse enum at path '" + path + "'", e);
        }
    }
}
