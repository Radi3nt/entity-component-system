package fr.radi3nt.ecs.loadable.json.parser.values.parsers;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.json.JsonValue;

public interface JsonValueParser {

    Object parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException;

}
