package fr.radi3nt.ecs.entity;

import fr.radi3nt.ecs.components.Component;
import fr.radi3nt.ecs.system.SystemHolder;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ECSObjectEntity implements ECSEntity {

    private final Map<Class<?>, Component> components = new ConcurrentHashMap<>();
    private final SystemHolder systemHolder;
    private boolean enabled;

    public ECSObjectEntity(SystemHolder systemHolder) {
        this.systemHolder = systemHolder;
    }

    @Override
    public <T extends Component> Optional<T> getComponent(Class<T> tClass) {
        return Optional.ofNullable((T) components.get(tClass));
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        for (Component value : components.values()) {
            value.notifyEntityChangeEnabledState();
        }
    }

    @Override
    public void destroy() {
        for (Component value : components.values()) {
            removeComponent(value);
        }
    }

    @Override
    public void addComponent(Component component) {
        Component old = components.put(component.getClass(), component);
        if (old != null) {
            removeComponent(old);
        }
        component.add(this);
        systemHolder.add(component);
    }

    @Override
    public void removeComponent(Class<?> tClass) {
        Component old = components.remove(tClass);
        if (old != null) {
            removeComponent(old);
        }
    }

    private void removeComponent(Component old) {
        systemHolder.remove(old);
        old.remove();
    }

}