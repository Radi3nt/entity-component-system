package fr.radi3nt.ecs.main;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.entity.provider.ObjectECSEntityProvider;
import fr.radi3nt.ecs.loadable.json.exceptions.JsonComponentParseException;
import fr.radi3nt.ecs.loadable.json.parser.MapPersistentComponentParserRegistry;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.*;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.DoubleJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.FloatJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.IntJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.LongJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.registry.ClassJsonValueParserRegistry;
import fr.radi3nt.ecs.loadable.json.parser.values.registry.MapClassJsonValueParserRegistry;
import fr.radi3nt.ecs.loadable.json.parser.variables.VariableStorage;
import fr.radi3nt.ecs.loadable.json.JsonEntityBlueprintLoader;
import fr.radi3nt.ecs.loadable.json.parser.values.JsonCustomValuesParser;
import fr.radi3nt.ecs.loadable.persistence.loader.MapPersistentComponentLoaderRegistry;
import fr.radi3nt.ecs.loadable.persistence.loader.PersistentComponentLoader;
import fr.radi3nt.ecs.loadable.persistence.loader.PersistentComponentLoaderRegistry;
import fr.radi3nt.ecs.loadable.persistence.loader.ReflectionPersistentComponentLoader;
import fr.radi3nt.ecs.loadable.json.parser.MappingPersistentComponentParser;
import fr.radi3nt.ecs.loadable.json.parser.PersistentComponentParser;
import fr.radi3nt.ecs.loadable.json.parser.PersistentComponentParserRegistry;
import fr.radi3nt.ecs.loadable.registry.MapComponentRegistry;
import fr.radi3nt.ecs.main.components.*;
import fr.radi3nt.ecs.persistence.blueprint.EntityBlueprint;
import fr.radi3nt.ecs.persistence.blueprint.comp.ReflectionComponentBlueprint;
import fr.radi3nt.ecs.persistence.data.MappedPersistentData;
import fr.radi3nt.ecs.persistence.exception.ComponentPersistenceException;
import fr.radi3nt.ecs.system.registerer.ListedGlobalComponentSystemRegisterer;
import fr.radi3nt.json.Json;
import fr.radi3nt.json.JsonValue;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainECSDemo {

    public static void main(String[] args) throws IOException, ComponentPersistenceException, JsonComponentParseException {
        Map<String, Class<? extends Component>> ids = new HashMap<>();
        ids.put("health", HealthComponent.class);
        ids.put("maxHealth", MaxHealthComponent.class);
        ids.put("deathState", DeathStateComponent.class);
        ids.put("deathOnHealthReachesZero", DeathOnHealthReachesZeroComponent.class);
        ids.put("movement", MovementComponent.class);

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
        parserMap.put(DynamicsConstants.class, new JsonValueParser() {
            @Override
            public Object parseCustom(JsonValue value, String path, VariableStorage storage) throws JsonComponentParseException {
                if (value.isArray()) {
                    ArrayJsonValueParser parser = new ArrayJsonValueParser(float.class, new EncapsulatingVariableJsonValueParser(FloatJsonValueParser.INSTANCE));
                    float[] array = (float[]) parser.parseCustom(value, path, storage);
                    return new DynamicsConstants(array[0], array[1], array[2]);
                } else if (value.isObject()) {
                    MappedPersistentData data = new MappingPersistentComponentParser('.', new JsonCustomValuesParser(JsonCustomValuesParser.fromFields(parserRegistry, DynamicsConstants.class))).parse(value.asObject(), storage);
                    return new DynamicsConstants((float) data.get("f"), (float) data.get("z"), (float) data.get("r"));
                }
                return null;
            }
        });

        Map<Class<? extends Component>, PersistentComponentLoader> loaderMap = new HashMap<>();

        JsonEntityBlueprintLoader loader = new JsonEntityBlueprintLoader(new MapComponentRegistry(ids), new MapPersistentComponentParserRegistry(new HashMap<>(), parserRegistry), new MapPersistentComponentLoaderRegistry(loaderMap));

        JsonValue object = Json.parse(new InputStreamReader(MainECSDemo.class.getResourceAsStream("/demo_entity.json")));
        EntityBlueprint blueprint = loader.load(object.asObject());

        ECSEntity entity = blueprint.create(new ObjectECSEntityProvider(new ListedGlobalComponentSystemRegisterer(new ArrayList<>())));
        entity.setEnabled(true);

        entity.getComponent(HealthComponent.class).ifPresent((healthComponent -> healthComponent.health=0));

        DeathOnHealthReachesZeroSystem.INSTANCE.update();

        System.out.println("hey");
    }

}
