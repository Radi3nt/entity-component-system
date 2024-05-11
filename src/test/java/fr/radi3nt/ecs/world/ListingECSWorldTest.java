package fr.radi3nt.ecs.world;

import fr.radi3nt.ecs.components.EntityComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListingECSWorldTest {

    private static ECSWorld world;

    @BeforeAll
    static void beforeAll() {
        world = new ListingECSWorld();
    }

    @Test
    void getComponentIteratorOnEmptyWorld() {
        Assertions.assertFalse(world.getComponentIterator(DemoComponent.class).hasNext(), "Iterator should be empty since no entities exist");
    }

    private static class DemoComponent extends EntityComponent {

    }
}