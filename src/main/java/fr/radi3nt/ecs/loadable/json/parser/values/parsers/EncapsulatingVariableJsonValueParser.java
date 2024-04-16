package fr.radi3nt.ecs.loadable.json.parser.values.parsers;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.json.JsonObject;
import fr.radi3nt.json.JsonValue;

public class EncapsulatingVariableJsonValueParser implements JsonValueParser {

    private final JsonValueParser underlying;

    public EncapsulatingVariableJsonValueParser(JsonValueParser underlying) {
        this.underlying = underlying;
    }

    @Override
    public Object parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException {
        if (value.isObject()) {
            JsonObject jsonObject = value.asObject();
            JsonValue stringValue = jsonObject.get("type");
            if (stringValue!=null && stringValue.isString() && stringValue.asString().equalsIgnoreCase("constant")) {
                value = storage.getReference(jsonObject.getString("name"));
            }
        }
        return underlying.parseCustom(value, path, storage);
    }
}
