package fr.radi3nt.ecs.system.schedule;

public interface SystemScheduling {

    void add(SystemUpdatable systemUpdatable);

    void remove(SystemUpdatable systemUpdatable);

}
