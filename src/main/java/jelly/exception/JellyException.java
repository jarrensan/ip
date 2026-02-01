package jelly.exception;

public class JellyException extends Exception {
    /**
     * Creates JellyException with specified error message.
     *
     * @param s Error message describing the error.
     */
    public JellyException(String s) {
        super("Uh-oh.... " + s);
    }
}
