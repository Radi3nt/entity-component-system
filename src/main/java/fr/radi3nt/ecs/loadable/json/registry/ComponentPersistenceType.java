package fr.radi3nt.ecs.loadable.json.registry;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.loadable.json.parser.MappingPersistentComponentParser;
import fr.radi3nt.ecs.loadable.json.parser.PersistentComponentParser;
import fr.radi3nt.ecs.loadable.json.parser.values.JsonCustomValuesParser;
import fr.radi3nt.ecs.loadable.json.parser.values.registry.ClassJsonValueParserRegistry;
import fr.radi3nt.ecs.loadable.persistence.loader.AnnotationReflectionPersistentComponentLoader;
import fr.radi3nt.ecs.loadable.persistence.loader.PersistentComponentLoader;

public class ComponentPersistenceType {

    public final PersistentComponentLoader loader;
    public final PersistentComponentParser parser;

    public ComponentPersistenceType(PersistentComponentLoader loader, PersistentComponentParser parser) {
        this.loader = loader;
        this.parser = parser;
    }

    public static ComponentPersistenceType fromReflection(ClassJsonValueParserRegistry registry, Class<? extends Component> aClass) {
        return new ComponentPersistenceType(new AnnotationReflectionPersistentComponentLoader(aClass), new MappingPersistentComponentParser('.', new JsonCustomValuesParser(JsonCustomValuesParser.fromFields(registry, aClass))));
    }
}
