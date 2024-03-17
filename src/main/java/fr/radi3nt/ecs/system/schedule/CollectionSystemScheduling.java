package fr.radi3nt.ecs.system.schedule;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionSystemScheduling implements SystemScheduling {

    private final Collection<SystemUpdatable> systemUpdatables = new ArrayList<>();

    @Override
    public void add(SystemUpdatable systemUpdatable) {
        systemUpdatables.add(systemUpdatable);
    }

    @Override
    public void remove(SystemUpdatable systemUpdatable) {
        systemUpdatables.remove(systemUpdatable);
    }
}
