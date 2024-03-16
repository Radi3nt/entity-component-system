package fr.radi3nt.ecs.loadable.json.parser.values.parsers;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;
import fr.radi3nt.json.JsonValue;

public class BooleanJsonValueParser implements JsonValueParser {

    public static final BooleanJsonValueParser INSTANCE = new BooleanJsonValueParser();

    @Override
    public Object parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException {
        if (value==null || !value.isBoolean()) {
            throw JsonComponentParseException.shouldValue(path, "a boolean");
        }
        return value.asBoolean();
    }
}
