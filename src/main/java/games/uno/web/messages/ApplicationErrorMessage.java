package games.uno.web.messages;

public class ApplicationErrorMessage
{
    private String message;

    public ApplicationErrorMessage(String message) {
        this.message = message;
    }

    public ApplicationErrorMessage(Throwable ex) {
        this(ex.getMessage());
    }

    public String getMessage() {
        return message;
    }
}
