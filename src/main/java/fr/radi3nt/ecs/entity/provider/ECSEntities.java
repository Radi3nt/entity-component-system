package fr.radi3nt.ecs.entity.provider;

import fr.radi3nt.ecs.entity.ECSEntity;

public final class ECSEntities {

    private static ECSEntityProvider provider;

    public static void setProvider(ECSEntityProvider provider) {
        ECSEntities.provider = provider;
    }

    public static ECSEntity entity() {
        return provider.entity();
    }

    public static ECSEntityProvider getProvider() {
        return provider;
    }
}
