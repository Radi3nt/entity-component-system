package fr.radi3nt.ecs.loadable.json.parser.variables;

import fr.radi3nt.json.JsonValue;

public interface VariableStorage {

    JsonValue getReference(String variableName);

}
