package fr.radi3nt.ecs.loadable.json;

import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
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
import fr.radi3nt.json.WriterConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JsonEntityBlueprintLoader {

    private final ComponentBlueprintRegistry registry;

    public JsonEntityBlueprintLoader(ComponentBlueprintRegistry registry) {
        this.registry = registry;
    }

    public EntityBlueprint load(JsonObject entityObject) throws JsonComponentParseException {
        JsonObject components = getObjectSafely(entityObject, "components");
        VariableStorage variableStorage = getVariableStorage(entityObject);

        Map<ComponentPersistenceType, ComponentBlueprint> componentBlueprints = new HashMap<>();

        for (JsonObject.Member component : components) {
            String componentId = component.getName();
            JsonObject componentObject = getAsObjectSafely(component.getValue(), "components." + componentId);

            ComponentPersistenceType type = registry.get(componentId);

            PersistentData parsedData = type.parser.parse(componentObject, variableStorage);
            ComponentBlueprint factoryWithData = new PersistentDataComponentBlueprint(type.loader, parsedData);

            componentBlueprints.put(type, factoryWithData);
        }

        return new EntityBlueprint(componentBlueprints.values());
    }

    private static VariableStorage getVariableStorage(JsonObject entityObject) throws JsonComponentParseException {
        Optional<JsonValue> variablesOptional = getOptionally(entityObject, "variables");
        if (!variablesOptional.isPresent())
            return new MapVariableStorage(new HashMap<>());

        JsonObject variables = getAsObjectSafely(variablesOptional.get(), "variables");
        return getVariableStorageFromJsonObject(variables);
    }

    private static VariableStorage getVariableStorageFromJsonObject(JsonObject variables) {
        Map<String, JsonValue> variableValues = new HashMap<>();
        for (JsonObject.Member variable : variables) {
            variableValues.put(variable.getName(), variable.getValue());
        }

        return new MapVariableStorage(variableValues);
    }

    private static JsonObject getObjectSafely(JsonObject root, String path) throws JsonComponentParseException {
        JsonValue value = getSafely(root, path);
        return getAsObjectSafely(value, path);
    }

    private static JsonObject getAsObjectSafely(JsonValue value, String valuePathForException) throws JsonComponentParseException {
        if (!value.isObject())
            throw new JsonComponentParseException("Value at path '" + valuePathForException + "' in parsed entity is not an object, and is " + value.toString(WriterConfig.PRETTY_PRINT));
        return value.asObject();
    }

    private static JsonValue getSafely(JsonObject entityObject, String path) throws JsonComponentParseException {
        JsonValue jsonValue = entityObject.get(path);
        if (jsonValue==null)
            throw new JsonComponentParseException("Couldn't find value at path '" + path + "' in parsed entity");
        return jsonValue;
    }

    private static Optional<JsonValue> getOptionally(JsonObject entityObject, String path) {
        JsonValue jsonValue = entityObject.get(path);
        return Optional.ofNullable(jsonValue);
    }

}
