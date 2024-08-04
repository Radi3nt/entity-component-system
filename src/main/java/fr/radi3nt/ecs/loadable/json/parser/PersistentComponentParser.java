package fr.radi3nt.ecs.loadable.json.parser;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.persistence.data.PersistentData;
import fr.radi3nt.json.JsonObject;
import fr.radi3nt.json.JsonValue;

public interface PersistentComponentParser {

    PersistentData parse(JsonValue jsonObject, VariableStorage variableStorage) throws JsonComponentParseException;

}
