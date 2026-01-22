package exception;

public class InvalidCommandException extends JellyException {
    public InvalidCommandException() {
        super("Invalid Command! Please try again!");
    }
}
