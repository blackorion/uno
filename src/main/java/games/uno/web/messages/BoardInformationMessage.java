package games.uno.web.messages;

public class BoardInformationMessage
{
    private String message;

    public BoardInformationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
