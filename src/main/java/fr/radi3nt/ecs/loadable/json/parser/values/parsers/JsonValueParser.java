package fr.radi3nt.ecs.loadable.json.parser.values.parsers;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.json.JsonObject;
import fr.radi3nt.json.JsonValue;

public interface JsonValueParser {

    Object parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException;

    static Object parseObject(JsonValueParser parser, String name, JsonObject object, String path, VariableStorage storage) throws JsonComponentParseException {
        return parser.parseCustom(object.get(name), path + "." + name, storage);
    }
    static JsonObject ensureObject(JsonValue value, String path) throws JsonComponentParseException {
        if (value==null)
            throw JsonComponentParseException.shouldPresent(path);
        if (!value.isObject())
            throw JsonComponentParseException.shouldObject(path);
        return value.asObject();
    }

}
