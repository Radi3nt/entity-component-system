package fr.radi3nt.ecs.loadable.json.exceptions;

public class ComponentPersistenceTypeNotFound extends RuntimeException {

    public ComponentPersistenceTypeNotFound() {
    }

    public ComponentPersistenceTypeNotFound(String message) {
        super(message);
    }

    public ComponentPersistenceTypeNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentPersistenceTypeNotFound(Throwable cause) {
        super(cause);
    }

    public ComponentPersistenceTypeNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
