package fr.radi3nt.ecs.main;

import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.entity.provider.ObjectECSEntityProvider;
import fr.radi3nt.ecs.entity.world.ECSWorld;
import fr.radi3nt.ecs.entity.world.ListingECSWorld;
import fr.radi3nt.ecs.loadable.json.JsonEntityBlueprintLoader;
import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.MappingPersistentComponentParser;
import fr.radi3nt.ecs.loadable.json.parser.values.JsonCustomValuesParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.*;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.DoubleJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.FloatJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.IntJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.LongJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.registry.ClassJsonValueParserRegistry;
import fr.radi3nt.ecs.loadable.json.parser.values.registry.MapClassJsonValueParserRegistry;
import fr.radi3nt.ecs.loadable.json.registry.ComponentPersistenceType;
import fr.radi3nt.ecs.loadable.json.registry.MapComponentBlueprintRegistry;
import fr.radi3nt.ecs.main.components.*;
import fr.radi3nt.ecs.persistence.blueprint.EntityBlueprint;
import fr.radi3nt.ecs.persistence.data.MappedPersistentData;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;
import fr.radi3nt.ecs.system.ComponentMapper;
import fr.radi3nt.json.Json;
import fr.radi3nt.json.JsonValue;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainECSDemo {

    public static void main(String[] args) throws IOException, ComponentPersistenceException, JsonComponentParseException {
        Map<Class<?>, JsonValueParser> parserMap = new HashMap<>();
        ClassJsonValueParserRegistry parserRegistry = new MapClassJsonValueParserRegistry(parserMap);

        parserMap.put(Integer.class, IntJsonValueParser.INSTANCE);
        parserMap.put(int.class, IntJsonValueParser.INSTANCE);
        parserMap.put(Long.class, LongJsonValueParser.INSTANCE);
        parserMap.put(long.class, LongJsonValueParser.INSTANCE);
        parserMap.put(Float.class, FloatJsonValueParser.INSTANCE);
        parserMap.put(float.class, FloatJsonValueParser.INSTANCE);
        parserMap.put(Double.class, DoubleJsonValueParser.INSTANCE);
        parserMap.put(double.class, DoubleJsonValueParser.INSTANCE);
        parserMap.put(Boolean.class, BooleanJsonValueParser.INSTANCE);
        parserMap.put(boolean.class, BooleanJsonValueParser.INSTANCE);
        parserMap.put(String.class, StringJsonValueParser.INSTANCE);
        parserMap.put(DynamicsConstants.class, (value, path, storage) -> {
            if (value.isArray()) {
                ArrayJsonValueParser parser = new ArrayJsonValueParser(float.class, new EncapsulatingVariableJsonValueParser(FloatJsonValueParser.INSTANCE));
                float[] array = (float[]) parser.parseCustom(value, path, storage);
                return new DynamicsConstants(array[0], array[1], array[2]);
            } else if (value.isObject()) {
                MappedPersistentData data = new MappingPersistentComponentParser('.', new JsonCustomValuesParser(JsonCustomValuesParser.fromFields(parserRegistry, DynamicsConstants.class))).parse(value.asObject(), storage);
                return new DynamicsConstants((float) data.get("f"), (float) data.get("z"), (float) data.get("r"));
            }
            return null;
        });

        Map<String, ComponentPersistenceType> componentPersistenceTypeMap = new HashMap<>();
        componentPersistenceTypeMap.put("health", ComponentPersistenceType.fromReflection(parserRegistry, HealthComponent.class));
        componentPersistenceTypeMap.put("maxHealth", ComponentPersistenceType.fromReflection(parserRegistry, MaxHealthComponent.class));
        componentPersistenceTypeMap.put("deathState", ComponentPersistenceType.fromReflection(parserRegistry, DeathStateComponent.class));
        componentPersistenceTypeMap.put("deathOnHealthReachesZero", ComponentPersistenceType.fromReflection(parserRegistry, DeathOnHealthReachesZeroComponent.class));
        componentPersistenceTypeMap.put("movement", ComponentPersistenceType.fromReflection(parserRegistry, MovementComponent.class));

        MapComponentBlueprintRegistry blueprintRegistry = new MapComponentBlueprintRegistry(componentPersistenceTypeMap);

        JsonEntityBlueprintLoader loader = new JsonEntityBlueprintLoader(blueprintRegistry);

        JsonValue object = Json.parse(new InputStreamReader(MainECSDemo.class.getResourceAsStream("/demo_entity.json")));
        EntityBlueprint blueprint = loader.load(object.asObject());

        ECSWorld world = new ListingECSWorld(new ComponentMapper(new ArrayList<>()));
        ECSEntity entity = blueprint.create(new ObjectECSEntityProvider());
        world.addEntity(entity);
        entity.getComponent(HealthComponent.class).health = 0;

        DeathOnHealthReachesZeroSystem.INSTANCE.update();

        System.out.println("hey");
    }

}
