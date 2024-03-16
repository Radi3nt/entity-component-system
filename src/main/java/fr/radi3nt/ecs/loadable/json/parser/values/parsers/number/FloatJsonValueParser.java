package fr.radi3nt.ecs.loadable.json.parser.values.parsers.number;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.JsonValueParser;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;
import fr.radi3nt.json.JsonValue;

public class FloatJsonValueParser extends NumberJsonValueParser implements JsonValueParser {

    public static final FloatJsonValueParser INSTANCE = new FloatJsonValueParser();

    @Override
    public Float parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException {
        if (value==null || !value.isNumber()) {
            throw JsonComponentParseException.shouldValue(path, "a float");
        }
        Number numberValue = getNumber(value, path);
        return numberValue.floatValue();
    }
}
