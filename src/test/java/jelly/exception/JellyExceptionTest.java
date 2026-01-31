package jelly.exception;

public class JellyExceptionTest extends Exception {
    public JellyExceptionTest(String s) {
        super("Uh-oh.... " + s);
    }
}
