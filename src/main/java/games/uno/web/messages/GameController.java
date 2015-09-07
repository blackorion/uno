package games.uno.web.messages;

import games.uno.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/game")
public class GameController
{
    @Autowired
    GameService gameService;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String startGame() {
        if ( gameService.startNewGame() )
            return "Game started";
        else
            return "Game start error";
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public String stopGame() {
        gameService.stopCurrentGame();

        return "Game stopped";
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public GameInfo info() {
        return gameService.getInfo();
    }

    @MessageExceptionHandler
    @SendTo("/queue/errors")
    public ApplicationError handleException(IllegalArgumentException exception) {
        return new ApplicationError(exception.getMessage());
    }
}
