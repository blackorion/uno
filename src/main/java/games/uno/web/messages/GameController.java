package games.uno.web.messages;

import games.uno.GameService;
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
    public GameInfo info() {
        return gameService.getInfo();
    }

    @MessageMapping(value = "/game.control")
    @SendTo("/topic/game.info")
    public GameInfo control(GameControlMessage control) {
        if ( "START".equals(control.getAction()) )
            gameService.startNewGame();
        else if ( "STOP".equals(control.getAction()) )
            gameService.stopCurrentGame();

        return gameService.getInfo();
    }

    @MessageExceptionHandler
    @SendTo("/queue/errors")
    public ApplicationError handleException(RuntimeException exception) {
        return new ApplicationError(exception.getMessage());
    }
}
