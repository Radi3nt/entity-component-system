package fr.radi3nt.ecs.loadable.json.parser;

public interface PersistentComponentParserRegistry {

    PersistentComponentParser getParserFromClass(Class<?> componentType);

}
