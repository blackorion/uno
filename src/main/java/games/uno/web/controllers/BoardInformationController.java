package games.uno.web.controllers;

import games.uno.web.messages.BoardInformationMessage;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class BoardInformationController
{
    @SendTo("/topic/board/information")
    public BoardInformationMessage information() {
        return new BoardInformationMessage("test message");
    }
}
