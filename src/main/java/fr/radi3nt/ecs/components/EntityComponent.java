package fr.radi3nt.ecs.components;

import fr.radi3nt.ecs.entity.ECSEntity;

public abstract class EntityComponent implements Component {

    public ECSEntity current;
    private boolean targetEnable = true;
    private boolean currentlyEnabled = false;

    @Override
    public void add(ECSEntity entity) {
        current = entity;
        triggerEnableIfPossibleWantedAndNeeded();
    }

    @Override
    public void remove() {
        disableIfNeeded();
        current = null;
    }

    @Override
    public boolean isEnabled() {
        return currentlyEnabled;
    }

    @Override
    public Component setEnabled(boolean enabled) {
        this.targetEnable = enabled;

        triggerEnableIfPossibleWantedAndNeeded();

        return this;
    }

    private void triggerEnableIfPossibleWantedAndNeeded() {
        if (current!=null && current.isEnabled()) {
            hardSetEnable(targetEnable);
        }
    }

    @Override
    public void notifyEntityChangeEnabledState() {
        if (current.isEnabled()) {
            hardSetEnable(targetEnable);
        } else
            hardSetEnable(false);
    }

    private void hardSetEnable(boolean enabled) {
        if (enabled)
            enableIfNeeded();
        else
            disableIfNeeded();
    }

    private void enableIfNeeded() {
        if (!currentlyEnabled)
            enable();
        currentlyEnabled = true;
    }

    protected void disableIfNeeded() {
        if (currentlyEnabled)
            disable();
        currentlyEnabled = false;
    }

    protected void enable() {

    }

    protected void disable() {

    }

}
