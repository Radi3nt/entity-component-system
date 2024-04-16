package fr.radi3nt.ecs.components;

import fr.radi3nt.ecs.entity.ECSEntity;

public abstract class EnableableComponent extends EntityComponent {

    private boolean enabled;

    @Override
    public void add(ECSEntity entity) {
        super.add(entity);
        enable();
    }

    @Override
    public void remove() {
        disable();
        super.remove();
    }

    public void enable() {
        if (!enabled) {
            enableComponent();
            enabled = true;
        }
    }

    public void disable() {
        if (enabled) {
            enabled = false;
            disableComponent();
        }
    }

    protected void enableComponent() {}
    protected void disableComponent() {}

    public boolean isEnabled() {
        return enabled;
    }
}
