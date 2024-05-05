package fr.radi3nt.ecs.world;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.entity.ECSEntity;

import java.util.Collection;

public interface ECSEntityProvider {

    ECSEntity create(Component... components);
    ECSEntity create(Collection<? extends Component> components);

}
