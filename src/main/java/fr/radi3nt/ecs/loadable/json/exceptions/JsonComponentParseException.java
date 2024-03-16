package fr.radi3nt.ecs.loadable.json.exceptions;

public class JsonComponentParseException extends Exception {

    public static JsonComponentParseException shouldPresent(String path) {
        return shouldValue(path, "present");
    }

    public static JsonComponentParseException shouldObject(String path) {
        return shouldValue(path, "an object");
    }

    public static JsonComponentParseException shouldArray(String path) {
        return shouldValue(path, "an array");
    }

    public static JsonComponentParseException shouldValue(String path, String should) {
        return new JsonComponentParseException("Value at path '" + path + "'" + " should be " + should);
    }

    public JsonComponentParseException() {
    }

    public JsonComponentParseException(String message) {
        super(message);
    }

    public JsonComponentParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonComponentParseException(Throwable cause) {
        super(cause);
    }

    public JsonComponentParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
