package fr.radi3nt.ecs.loadable.persistence.loader.loaders;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface ComponentBuilderPersistent {

}
