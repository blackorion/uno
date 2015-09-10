package games.uno.web.messages;

public class PlayerActionMessage
{
    public PlayerAction action;
    public String message;

    private enum PlayerAction
    {
        CHANGE_NAME
    }
}
