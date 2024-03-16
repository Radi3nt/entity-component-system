package fr.radi3nt.ecs.loadable.json;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.variables.MapVariableStorage;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.loadable.registry.ComponentRegistry;
import fr.radi3nt.ecs.loadable.persistence.loader.PersistentComponentLoaderRegistry;
import fr.radi3nt.ecs.loadable.json.parser.PersistentComponentParser;
import fr.radi3nt.ecs.loadable.json.parser.PersistentComponentParserRegistry;
import fr.radi3nt.ecs.persistence.blueprint.EntityBlueprint;
import fr.radi3nt.ecs.persistence.blueprint.comp.ComponentBlueprint;
import fr.radi3nt.ecs.persistence.blueprint.comp.PersistentDataComponentBlueprint;
import fr.radi3nt.ecs.persistence.data.PersistentData;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;
import fr.radi3nt.json.JsonObject;
import fr.radi3nt.json.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonEntityBlueprintLoader {

    private final ComponentRegistry componentRegistry;
    private final PersistentComponentParserRegistry parserRegistry;
    private final PersistentComponentLoaderRegistry loaderRegistry;

    public JsonEntityBlueprintLoader(ComponentRegistry componentRegistry, PersistentComponentParserRegistry parserRegistry, PersistentComponentLoaderRegistry loaderRegistry) {
        this.componentRegistry = componentRegistry;
        this.parserRegistry = parserRegistry;
        this.loaderRegistry = loaderRegistry;
    }

    public EntityBlueprint load(JsonObject entityObject) throws JsonComponentParseException {
        JsonObject components = entityObject.get("components").asObject();
        JsonObject variables = entityObject.get("variables").asObject();

        Map<String, JsonValue> variableValues = new HashMap<>();

        for (JsonObject.Member variable : variables) {
            variableValues.put(variable.getName(), variable.getValue());
        }

        VariableStorage variableStorage = new MapVariableStorage(variableValues);

        LinkedHashMap<Class<?>, ComponentBlueprint> componentBlueprints = new LinkedHashMap<>();

        for (JsonObject.Member component : components) {
            String componentId = component.getName();
            JsonObject componentObject = component.getValue().asObject();

            Class<? extends Component> componentClass = componentRegistry.getComponentClassById(componentId);
            PersistentComponentParser parser = parserRegistry.getParserFromClass(componentClass);

            PersistentData parsedData = parser.parse(componentObject, variableStorage);

            ComponentBlueprint factoryWithData = new PersistentDataComponentBlueprint(componentClass, loaderRegistry.getLoaderFromClass(componentClass), parsedData);

            componentBlueprints.put(componentClass, factoryWithData);
        }

        return new EntityBlueprint(new ArrayList<>(componentBlueprints.values()));
    }

}
