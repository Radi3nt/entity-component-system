package fr.radi3nt.ecs.loadable.json.parser.values.parsers;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;
import fr.radi3nt.json.JsonValue;

public class StringJsonValueParser implements JsonValueParser {

    public static final StringJsonValueParser INSTANCE = new StringJsonValueParser();

    @Override
    public Object parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException {
        if (value==null || !value.isString()) {
            throw JsonComponentParseException.shouldValue(path, "a string");
        }
        return value.asString();
    }
}
