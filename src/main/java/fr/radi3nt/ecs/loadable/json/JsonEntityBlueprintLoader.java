package fr.radi3nt.ecs.loadable.json;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.PersistentComponentParser;
import fr.radi3nt.ecs.loadable.json.parser.variables.MapVariableStorage;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.loadable.json.registry.ComponentBlueprintRegistry;
import fr.radi3nt.ecs.loadable.json.registry.ComponentPersistenceType;
import fr.radi3nt.ecs.persistence.blueprint.EntityBlueprint;
import fr.radi3nt.ecs.persistence.blueprint.comp.ComponentBlueprint;
import fr.radi3nt.ecs.persistence.blueprint.comp.PersistentDataComponentBlueprint;
import fr.radi3nt.ecs.persistence.data.PersistentData;
import fr.radi3nt.json.JsonObject;
import fr.radi3nt.json.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonEntityBlueprintLoader {

    private final ComponentBlueprintRegistry registry;

    public JsonEntityBlueprintLoader(ComponentBlueprintRegistry registry) {
        this.registry = registry;
    }

    public EntityBlueprint load(JsonObject entityObject) throws JsonComponentParseException {
        JsonObject components = entityObject.get("components").asObject();
        JsonObject variables = entityObject.get("variables").asObject();

        Map<String, JsonValue> variableValues = new HashMap<>();

        for (JsonObject.Member variable : variables) {
            variableValues.put(variable.getName(), variable.getValue());
        }

        VariableStorage variableStorage = new MapVariableStorage(variableValues);

        List<ComponentBlueprint> componentBlueprints = new ArrayList<>();

        for (JsonObject.Member component : components) {
            String componentId = component.getName();
            JsonObject componentObject = component.getValue().asObject();

            ComponentPersistenceType type = registry.get(componentId);
            PersistentComponentParser parser = type.parser;

            PersistentData parsedData = parser.parse(componentObject, variableStorage);

            ComponentBlueprint factoryWithData = new PersistentDataComponentBlueprint(type.loader, parsedData);

            componentBlueprints.add(factoryWithData);
        }

        return new EntityBlueprint(componentBlueprints);
    }

}
