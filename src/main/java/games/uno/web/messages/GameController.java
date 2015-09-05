package games.uno.web.messages;

import games.uno.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController
{
    @Autowired
    GameService gameService;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String startGame() {
        gameService.startNewGame();

        return "Game started";
    }
}
