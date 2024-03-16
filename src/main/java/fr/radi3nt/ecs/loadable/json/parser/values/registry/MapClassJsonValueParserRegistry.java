package fr.radi3nt.ecs.loadable.json.parser.values.registry;

import fr.radi3nt.ecs.loadable.json.parser.values.parsers.JsonValueParser;

import java.util.Map;
import java.util.Optional;

public class MapClassJsonValueParserRegistry implements ClassJsonValueParserRegistry {

    private final Map<Class<?>, JsonValueParser> parserMap;

    public MapClassJsonValueParserRegistry(Map<Class<?>, JsonValueParser> parserMap) {
        this.parserMap = parserMap;
    }

    @Override
    public Optional<JsonValueParser> getParserFromClass(Class<?> currentClass) {
        return Optional.ofNullable(parserMap.get(currentClass));
    }
}
