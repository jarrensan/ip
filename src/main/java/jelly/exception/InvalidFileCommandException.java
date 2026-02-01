package jelly.exception;

public class InvalidFileCommandException extends JellyException {
    public InvalidFileCommandException(String s) {
        super(s + ". Please check file again!");
    }
}
