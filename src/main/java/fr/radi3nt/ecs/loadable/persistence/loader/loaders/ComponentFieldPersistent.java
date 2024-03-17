package fr.radi3nt.ecs.loadable.persistence.loader.loaders;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ComponentFieldPersistent {

    boolean required() default false;
    String[] ids() default {};

}
