package fr.ans.psc.exception;

public class GenericVihfException extends Exception {
    public GenericVihfException(String message) {
        super(message);
    }
    public GenericVihfException(String message, Throwable cause) {
        super(message, cause);
    }
}
