package games.uno.web;

import games.uno.GameService;
import games.uno.PlayerService;
import games.uno.web.messages.ApplicationErrorMessage;
import games.uno.web.messages.GameControlMessage;
import games.uno.web.messages.GameInfoMessage;
import games.uno.web.messages.PlayCardEventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping("/api/game")
@RestController
public class GameController
{
    private GameService gameService;
    private PlayerService playerService;

    @Autowired
    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @SubscribeMapping("/game.info")
    public GameInfoMessage info() {
        return gameService.getInfo();
    }

    @MessageMapping(value = "/game.control")
    @SendTo("/topic/game.info")
    public GameInfoMessage control(GameControlMessage control) {
        control.getAction().invoke(gameService);

        return gameService.getInfo();
    }

    @MessageMapping("/game.playcard")
    @SendTo("/topic/game.info")
    public GameInfoMessage playCard(PlayCardEventMessage event, StompHeaderAccessor accessor) {
        gameService.playCard(playerService.find(accessor), event.card.getCard());

        return gameService.getInfo();
    }

    @MessageExceptionHandler
    @SendTo("/queue/errors")
    public ApplicationErrorMessage handleException(RuntimeException exception) {
        return new ApplicationErrorMessage(exception.getMessage());
    }
}
