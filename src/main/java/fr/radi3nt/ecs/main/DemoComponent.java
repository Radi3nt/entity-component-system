package fr.radi3nt.ecs.main;

import fr.radi3nt.ecs.components.EntityComponent;

public class DemoComponent extends EntityComponent {

    public final String aString;
    public final float aFloat;
    public final int aInt;
    public final int[] array;
    public final DemoEnum aEnum;

    public DemoComponent(String aString, float aFloat, int aInt, int[] array, DemoEnum aEnum) {
        this.aString = aString;
        this.aFloat = aFloat;
        this.aInt = aInt;
        this.array = array;
        this.aEnum = aEnum;
    }

    public DemoComponent() {
        aString = "";
        aFloat = 0;
        aInt = 0;
        array = new int[0];
        aEnum = DemoEnum.NO_READY;
    }
}
