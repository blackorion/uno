package games.uno.web;

import games.uno.GameService;
import games.uno.web.messages.ApplicationErrorMessage;
import games.uno.web.messages.GameControlMessage;
import games.uno.web.messages.GameInfoMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping("/api/game")
@RestController
public class GameController
{
    @Autowired
    GameService gameService;

    @SubscribeMapping("/game.info")
    public GameInfoMessage info() {
        return gameService.getInfo();
    }

    @MessageMapping(value = "/game.control")
    @SendTo("/topic/game.info")
    public GameInfoMessage control(GameControlMessage control) {
        if ( "START".equals(control.getAction()) )
            gameService.startNewGame();
        else if ( "STOP".equals(control.getAction()) )
            gameService.stopCurrentGame();

        return gameService.getInfo();
    }

    @MessageExceptionHandler
    @SendTo("/queue/errors")
    public ApplicationErrorMessage handleException(RuntimeException exception) {
        return new ApplicationErrorMessage(exception.getMessage());
    }
}
