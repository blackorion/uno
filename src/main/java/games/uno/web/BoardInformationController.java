package games.uno.web;

import games.uno.web.messages.BoardInformation;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class BoardInformationController
{
    @SendTo("/topic/board/information")
    public BoardInformation information() {
        return new BoardInformation("test message");
    }
}
