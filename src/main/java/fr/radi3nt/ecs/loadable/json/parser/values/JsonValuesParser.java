package fr.radi3nt.ecs.loadable.json.parser.values;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.JsonValueParser;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;
import fr.radi3nt.json.JsonValue;

public interface JsonValuesParser extends JsonValueParser {

    boolean isCustomValue(JsonValue value, String path);
    Object parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException;

}
