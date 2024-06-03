package fr.radi3nt.ecs.system.schedule;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SystemScheduler {

    private final Map<SystemUpdateKey, Collection<SystemUpdatable>> systemUpdatableMap = new ConcurrentHashMap<>();

    public void add(SystemUpdateKey systemUpdateKey, SystemUpdatable updatable) {
        Collection<SystemUpdatable> systemUpdatable = this.systemUpdatableMap.computeIfAbsent(systemUpdateKey, thatSameSystemUpdateKey -> ConcurrentHashMap.newKeySet());
        systemUpdatable.add(updatable);
    }

    public void update(SystemUpdateKey update, float delta) {
        Collection<SystemUpdatable> systemUpdatable = systemUpdatableMap.get(update);
        if (systemUpdatable == null)
            return;

        for (SystemUpdatable current : systemUpdatable) {
            current.update(delta);
        }
    }

}
