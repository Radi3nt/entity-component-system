package fr.radi3nt.ecs.loadable.json.parser.values.parsers.number;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.JsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.json.JsonValue;

public class LongJsonValueParser extends NumberJsonValueParser implements JsonValueParser {

    public static final LongJsonValueParser INSTANCE = new LongJsonValueParser();

    @Override
    public Object parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException {
        if (value==null || !value.isNumber()) {
            throw JsonComponentParseException.shouldValue(path, "a long");
        }
        Number numberValue = getNumber(value, path);
        return numberValue.longValue();
    }
}
