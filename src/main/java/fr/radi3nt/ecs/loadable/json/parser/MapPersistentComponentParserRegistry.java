package fr.radi3nt.ecs.loadable.json.parser;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.loadable.json.parser.values.JsonCustomValuesParser;
import fr.radi3nt.ecs.loadable.json.parser.values.registry.ClassJsonValueParserRegistry;

import java.util.Map;

public class MapPersistentComponentParserRegistry implements PersistentComponentParserRegistry {

    private final Map<Class<? extends Component>, PersistentComponentParser> parserMap;
    private final ClassJsonValueParserRegistry classParserRegistry;

    public MapPersistentComponentParserRegistry(Map<Class<? extends Component>, PersistentComponentParser> parserMap, ClassJsonValueParserRegistry classParserRegistry) {
        this.parserMap = parserMap;
        this.classParserRegistry = classParserRegistry;
    }

    @Override
    public PersistentComponentParser getParserFromClass(Class<?> componentType) {
        return parserMap.getOrDefault(componentType, new MappingPersistentComponentParser('.', new JsonCustomValuesParser(JsonCustomValuesParser.fromFieldsAndBuilders(classParserRegistry, componentType))));
    }
}
