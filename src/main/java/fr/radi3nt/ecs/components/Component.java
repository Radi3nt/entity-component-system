package fr.radi3nt.ecs.components;

import fr.radi3nt.ecs.entity.ECSEntity;

public interface Component {

    void add(ECSEntity entity);
    void remove();

}
