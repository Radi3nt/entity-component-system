package fr.radi3nt.ecs.components;

import fr.radi3nt.ecs.entity.ECSEntity;

public abstract class EntityComponent implements Component {

    public ECSEntity current;

    @Override
    public void add(ECSEntity entity) {
        current = entity;
    }

    @Override
    public void remove() {
        current = null;
    }

    public boolean isInWorld() {
        return current!=null && !current.isDestroyed();
    }
}
