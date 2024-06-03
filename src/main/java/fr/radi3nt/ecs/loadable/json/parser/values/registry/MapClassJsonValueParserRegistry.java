package fr.radi3nt.ecs.loadable.json.parser.values.registry;

import fr.radi3nt.ecs.loadable.json.parser.values.parsers.BooleanJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.JsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.StringJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.DoubleJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.FloatJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.IntJsonValueParser;
import fr.radi3nt.ecs.loadable.json.parser.values.parsers.number.LongJsonValueParser;

import java.util.Map;
import java.util.Optional;

public class MapClassJsonValueParserRegistry implements ClassJsonValueParserRegistry {

    private final Map<Class<?>, JsonValueParser> parserMap;

    public MapClassJsonValueParserRegistry(Map<Class<?>, JsonValueParser> parserMap) {
        this.parserMap = parserMap;
    }


    public static void putNativeParsers(Map<Class<?>, JsonValueParser> parserMap) {
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
    }

    @Override
    public Optional<JsonValueParser> getParserFromClass(Class<?> currentClass) {
        return Optional.ofNullable(parserMap.get(currentClass));
    }
}
