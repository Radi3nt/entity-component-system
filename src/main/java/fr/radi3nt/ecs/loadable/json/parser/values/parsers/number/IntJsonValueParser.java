package fr.radi3nt.ecs.loadable.json.parser.values.parsers.number;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.JsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.json.JsonValue;

public class IntJsonValueParser extends NumberJsonValueParser implements JsonValueParser {

    public static final IntJsonValueParser INSTANCE = new IntJsonValueParser();

    @Override
    public Integer parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException {
        if (value==null || !value.isNumber()) {
            throw JsonComponentParseException.shouldValue(path, "an int");
        }
        Number numberValue = getNumber(value, path);
        return numberValue.intValue();
    }
}
