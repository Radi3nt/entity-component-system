package fr.radi3nt.ecs.components;

import fr.radi3nt.ecs.system.ComponentSystem;

public abstract class SystemsComponent extends EntityComponent {

    private final ComponentSystem[] systems;

    protected SystemsComponent(ComponentSystem... systems) {
        this.systems = systems;
    }

    @Override
    protected void enable() {
        super.enable();
        for (ComponentSystem system : systems) {
            system.add(this);
        }
    }

    @Override
    protected void disable() {
        for (ComponentSystem system : systems) {
            system.remove(this);
        }
        super.disable();
    }

}
