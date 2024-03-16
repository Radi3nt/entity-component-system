package fr.radi3nt.ecs.loadable.json.parser.values.registry;

import fr.radi3nt.ecs.loadable.json.parser.values.parsers.JsonValueParser;

import java.util.Optional;

public interface ClassJsonValueParserRegistry {

    Optional<JsonValueParser> getParserFromClass(Class<?> currentClass);

}
