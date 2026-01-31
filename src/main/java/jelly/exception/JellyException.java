package jelly.exception;

public class JellyException extends Exception{
    public JellyException(String s) {
        super("Uh-oh.... " + s);
    }
}
