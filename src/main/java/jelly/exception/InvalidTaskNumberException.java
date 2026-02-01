package jelly.exception;

public class InvalidTaskNumberException extends JellyException {
    public InvalidTaskNumberException(int index) {
        super("Task number " + index + " does not exist!");
    }
}
