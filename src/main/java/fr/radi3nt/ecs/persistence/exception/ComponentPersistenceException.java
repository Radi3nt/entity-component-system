package fr.radi3nt.ecs.persistence.exception;

public class ComponentPersistenceException extends Exception {

    public ComponentPersistenceException() {
    }

    public ComponentPersistenceException(String message) {
        super(message);
    }

    public ComponentPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentPersistenceException(Throwable cause) {
        super(cause);
    }

    public ComponentPersistenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
