package games.uno.web.messages;

public class ApplicationError
{
    private String message;

    public ApplicationError(String message) {
        this.message = message;
    }

    public ApplicationError(Throwable ex) {
        this(ex.getMessage());
    }

    public String getMessage() {
        return message;
    }
}
