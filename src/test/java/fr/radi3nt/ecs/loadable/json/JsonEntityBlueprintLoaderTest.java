package fr.radi3nt.ecs.loadable.json;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.components.EntityComponent;
import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.loadable.json.exceptions.ComponentPersistenceTypeNotFound;
import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.*;
import fr.radi3nt.ecs.loadable.json.parser.values.registry.ClassJsonValueParserRegistry;
import fr.radi3nt.ecs.loadable.json.parser.values.registry.MapClassJsonValueParserRegistry;
import fr.radi3nt.ecs.loadable.json.registry.ComponentPersistenceType;
import fr.radi3nt.ecs.loadable.json.registry.MapComponentBlueprintRegistry;
import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentBuilderPersistent;
import fr.radi3nt.ecs.persistence.blueprint.EntityBlueprint;
import fr.radi3nt.ecs.world.ECSEntityProvider;
import fr.radi3nt.json.Json;
import fr.radi3nt.json.JsonValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class JsonEntityBlueprintLoaderTest {

    static MapComponentBlueprintRegistry blueprintRegistry;

    @BeforeAll
    static void beforeAll() {
        Map<Class<?>, JsonValueParser> parserMap = new HashMap<>();
        ClassJsonValueParserRegistry parserRegistry = new MapClassJsonValueParserRegistry(parserMap);

        Map<String, ComponentPersistenceType> componentPersistenceTypeMap = new HashMap<>();
        componentPersistenceTypeMap.put("testComponent", ComponentPersistenceType.fromReflection(parserRegistry, TestComponent.class));

        blueprintRegistry = new MapComponentBlueprintRegistry(componentPersistenceTypeMap);
    }


    public static class TestComponent extends EntityComponent {

        @ComponentBuilderPersistent
        public TestComponent() {
        }
    }


    @Test
    void loadMalformedMissingComponents() {
        Assertions.assertThrowsExactly(JsonComponentParseException.class, () -> loadEntityFromPath("malformed/missing/missing_components_entity.json"));
    }

    @Test
    void loadOptionalMissingVariables() throws JsonComponentParseException, IOException {
        Assertions.assertNotNull(loadEntityFromPath("optional/optional_variables_absent_entity.json"));
    }

    @Test
    void loadMalformedWrongTypeArrayComponents() {
        Assertions.assertThrowsExactly(JsonComponentParseException.class, () -> loadEntityFromPath("malformed/type/wrong_type_array_components_entity.json"));
    }

    @Test
    void loadMalformedWrongTypeArrayVariables() {
        Assertions.assertThrowsExactly(JsonComponentParseException.class, () -> loadEntityFromPath("malformed/type/wrong_type_array_variables_entity.json"));
    }

    @Test
    void loadMalformedWrongTypeStringComponents() {
        Assertions.assertThrowsExactly(JsonComponentParseException.class, () -> loadEntityFromPath("malformed/type/wrong_type_string_components_entity.json"));
    }

    @Test
    void loadMalformedWrongTypeStringVariables() {
        Assertions.assertThrowsExactly(JsonComponentParseException.class, () -> loadEntityFromPath("malformed/type/wrong_type_string_variables_entity.json"));
    }

    @Test
    void loadMalformedMissingComponentValueId() {
        Assertions.assertThrowsExactly(ComponentPersistenceTypeNotFound.class, () -> loadEntityFromPath("malformed/missing/component/missing_component_value_id_entity.json"));
    }

    @Test
    void loadContentTestComponent() throws JsonComponentParseException, IOException {
        EntityBlueprint blueprint = loadEntityFromPath("content/content_test_component_entity.json");
        blueprint.create(new ECSEntityProvider() {
            @Override
            public ECSEntity create(Component... components) {
                Assertions.assertEquals(components.length, 1, "More than one component (of the same type) was created");
                Assertions.assertNotNull(components[0]);
                Assertions.assertEquals(components[0].getClass(), TestComponent.class);
                return null;
            }

            @Override
            public ECSEntity create(Collection<? extends Component> components) {
                Assertions.assertEquals(components.size(), 1, "More than one component (of the same type) was created");
                Assertions.assertNotNull(components.stream().findAny().get());
                Assertions.assertEquals(components.stream().findAny().get().getClass(), TestComponent.class);
                return null;
            }
        });
    }

    @Test
    void loadContentDuplicatedTestComponent() throws JsonComponentParseException, IOException {
        EntityBlueprint blueprint = loadEntityFromPath("content/content_duplicated_test_component_entity.json");
        blueprint.create(new ECSEntityProvider() {
            @Override
            public ECSEntity create(Component... components) {
                Assertions.assertEquals(1, components.length, "More than one component (of the same type) was created");
                Assertions.assertNotNull(components[0]);
                Assertions.assertEquals(components[0].getClass(), TestComponent.class);
                return null;
            }

            @Override
            public ECSEntity create(Collection<? extends Component> components) {
                Assertions.assertEquals(1, components.size(), "More than one component (of the same type) was created");
                Assertions.assertNotNull(components.stream().findAny().get());
                Assertions.assertEquals(components.stream().findAny().get().getClass(), TestComponent.class);
                return null;
            }
        });
    }

    private EntityBlueprint loadEntityFromPath(String path) throws IOException, JsonComponentParseException {
        JsonValue object = Json.parse(new InputStreamReader(this.getClass().getResourceAsStream("/entities/" + path)));

        JsonEntityBlueprintLoader loader = new JsonEntityBlueprintLoader(blueprintRegistry);
        return loader.load(object.asObject());
    }
}