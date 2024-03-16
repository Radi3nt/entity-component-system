package fr.radi3nt.ecs.loadable.json.parser;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.persistence.data.PersistentData;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;
import fr.radi3nt.json.JsonObject;

public interface PersistentComponentParser {

    PersistentData parse(JsonObject jsonObject, VariableStorage variableStorage) throws JsonComponentParseException;

}
