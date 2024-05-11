package fr.radi3nt.ecs.loadable.json.registry;

import fr.radi3nt.ecs.loadable.json.exceptions.ComponentPersistenceTypeNotFound;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapComponentBlueprintRegistryTest {

    static MapComponentBlueprintRegistry registry;

    @BeforeAll
    static void beforeAll() {
        Map<String, ComponentPersistenceType> persistenceTypeMap = new HashMap<>();
        persistenceTypeMap.put("existentComponent", new ComponentPersistenceType(null, null));
        registry = new MapComponentBlueprintRegistry(persistenceTypeMap);
    }

    @Test
    void getAbsentThrowsException() {
        Assertions.assertThrowsExactly(ComponentPersistenceTypeNotFound.class, () -> registry.get("nonexistentComponent"));
    }

    @Test
    void getPresentThrowsException() {
        Assertions.assertNotNull(registry.get("existentComponent"));
    }


}