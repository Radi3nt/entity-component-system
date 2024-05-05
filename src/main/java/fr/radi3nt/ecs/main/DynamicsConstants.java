package fr.radi3nt.ecs.main;

import fr.radi3nt.ecs.loadable.persistence.loader.loaders.ComponentFieldPersistent;

public class DynamicsConstants {

    @ComponentFieldPersistent
    public final float f;
    @ComponentFieldPersistent
    public final float z;
    @ComponentFieldPersistent
    public final float r;

    public DynamicsConstants(float f, float z, float r) {
        this.f = f;
        this.z = z;
        this.r = r;
    }
}
