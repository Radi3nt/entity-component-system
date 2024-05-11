package fr.radi3nt.ecs.components;

import fr.radi3nt.ecs.entity.HandledEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

class EnableableComponentTest {

    @Test
    void disabledByDefault() {
        EnableableComponent enableableComponent = new EnableableComponent() {};
        Assertions.assertFalse(enableableComponent.isEnabled());
    }

    @Test
    void enabledAfterEntityCreation() {
        EnableableComponent enableableComponent = new EnableableComponent() {};
        enableableComponent.add(new HandledEntity(null));
        Assertions.assertTrue(enableableComponent.isEnabled());
    }

    @Test
    void disabledAfterDisabling() {
        EnableableComponent enableableComponent = new EnableableComponent() {};
        enableableComponent.disable();
        Assertions.assertFalse(enableableComponent.isEnabled());
    }

    @Test
    void enabledAfterEnabling() {
        EnableableComponent enableableComponent = new EnableableComponent() {};
        enableableComponent.enable();
        Assertions.assertTrue(enableableComponent.isEnabled());
    }

    @Test
    void enabledMethodCalled() {
        AtomicInteger calledTime = new AtomicInteger();
        EnableableComponent enableableComponent = calledEnableComponent(calledTime);
        enableableComponent.enable();
        Assertions.assertEquals(1, calledTime.get());
    }

    @Test
    void enabledMethodNotCalled() {
        AtomicInteger calledTime = new AtomicInteger();
        calledEnableComponent(calledTime);
        Assertions.assertEquals(0, calledTime.get());
    }

    @Test
    void enabledMethodCalledByEntityAdd() {
        AtomicInteger calledTime = new AtomicInteger();
        EnableableComponent enableableComponent = calledEnableComponent(calledTime);
        enableableComponent.add(new HandledEntity(null));
        Assertions.assertEquals(1, calledTime.get());
    }

    private static EnableableComponent calledEnableComponent(AtomicInteger calledTime) {
        return new EnableableComponent() {
            @Override
            protected void enableComponent() {
                super.enableComponent();
                calledTime.incrementAndGet();
            }
        };
    }


}