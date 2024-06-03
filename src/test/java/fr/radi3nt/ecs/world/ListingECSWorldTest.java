package fr.radi3nt.ecs.world;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.components.EntityComponent;
import fr.radi3nt.ecs.entity.ECSEntity;
import fr.radi3nt.ecs.world.listener.ComponentListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

class ListingECSWorldTest {

    private ECSWorld world;

    @BeforeEach
    void beforeEach() {
        world = new ListingECSWorld();
    }

    @Test
    void creatingEntityWithNoComponents() {
        ECSEntity ecsEntity = world.create();
        Assertions.assertNull(ecsEntity.getComponent(DemoComponent.class), "Entity should not have DemoComponent");
    }

    @Test
    void creatingEntityWithComponents() {
        ECSEntity ecsEntity = world.create(new DemoComponent());
        Assertions.assertNotNull(ecsEntity.getComponent(DemoComponent.class), "Entity should have a DemoComponent");
    }

    @Test
    void creatingEntityIsDestroyed() {
        ECSEntity ecsEntity = world.create();
        world.destroy(ecsEntity);

        Assertions.assertTrue(ecsEntity.isDestroyed());
    }

    @Test
    void creatingEntityIsNotDestroyed() {
        ECSEntity ecsEntity = world.create();
        Assertions.assertFalse(ecsEntity.isDestroyed());
    }

    @Test
    void getComponentIteratorOnEmptyWorld() {
        Assertions.assertFalse(world.getComponentIterator(DemoComponent.class).hasNext(), "Iterator should be empty since no entities exist");
    }

    @Test
    void creatingEntityWithNoComponentRemovedThemFromIterator() {
        ECSEntity ecsEntity = world.create();
        Assertions.assertFalse(world.getComponentIterator(DemoComponent.class).hasNext(), "Iterator should be empty since no entities exist");
    }

    @Test
    void creatingEntityWithComponentAreInIterator() {
        ECSEntity ecsEntity = world.create(new DemoComponent());
        Assertions.assertTrue(world.getComponentIterator(DemoComponent.class).hasNext(), "Iterator should not be empty since entities with the DemoComponent exist");
    }

    @Test
    void creatingEntityWithComponentRemovedThemFromIterator() {
        ECSEntity ecsEntity = world.create(new DemoComponent());
        world.destroy(ecsEntity);
        Assertions.assertFalse(world.getComponentIterator(DemoComponent.class).hasNext(), "Iterator should be empty since no entities exist");
    }

    @Test
    void listenerAddComponentTriggersListener() {
        AtomicInteger counter = new AtomicInteger();
        world.addListener(DemoComponent.class, new ComponentListener() {
            @Override
            public void added(Component component) {
                counter.incrementAndGet();
            }

            @Override
            public void removed(Component component) {

            }
        });
        world.create(new DemoComponent());

        Assertions.assertEquals(1, counter.get());
    }

    @Test
    void listenerAddComponentDoesntTriggerRemovedListener() {
        AtomicInteger counter = new AtomicInteger();
        ComponentListener listener = new ComponentListener() {
            @Override
            public void added(Component component) {
                counter.incrementAndGet();
            }

            @Override
            public void removed(Component component) {

            }
        };
        world.addListener(DemoComponent.class, listener);
        world.removeListener(DemoComponent.class, listener);

        world.create(new DemoComponent());

        Assertions.assertEquals(0, counter.get());
    }

    @Test
    void listenerNoComponentDoesntTriggerAddListener() {
        AtomicInteger counter = new AtomicInteger();
        world.addListener(DemoComponent.class, new ComponentListener() {
            @Override
            public void added(Component component) {
                counter.incrementAndGet();
            }

            @Override
            public void removed(Component component) {

            }
        });
        world.create();

        Assertions.assertEquals(0, counter.get());
    }

    @Test
    void listenerNoComponentDoesntTriggerRemoveListener() {
        AtomicInteger counter = new AtomicInteger();
        world.addListener(DemoComponent.class, new ComponentListener() {
            @Override
            public void added(Component component) {

            }

            @Override
            public void removed(Component component) {
                counter.incrementAndGet();
            }
        });
        ECSEntity ecsEntity = world.create();
        ecsEntity.destroy();

        Assertions.assertEquals(0, counter.get());
    }

    @Test
    void listenerRemoveComponentTriggersListener() {
        AtomicInteger counter = new AtomicInteger();
        world.addListener(DemoComponent.class, new ComponentListener() {
            @Override
            public void added(Component component) {

            }

            @Override
            public void removed(Component component) {
                counter.incrementAndGet();
            }
        });
        ECSEntity ecsEntity = world.create(new DemoComponent());
        ecsEntity.destroy();

        Assertions.assertEquals(1, counter.get());
    }

    private static class DemoComponent extends EntityComponent {

    }
}