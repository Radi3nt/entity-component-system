package fr.radi3nt.ecs.system.schedule;

public class SystemSchedule {

    private final SystemScheduler scheduler;
    private final SystemUpdateKey key;

    public SystemSchedule(SystemScheduler scheduler, SystemUpdateKey key) {
        this.scheduler = scheduler;
        this.key = key;
    }

    public void register(SystemUpdatable updatable) {
        scheduler.add(key, updatable);
    }
}
