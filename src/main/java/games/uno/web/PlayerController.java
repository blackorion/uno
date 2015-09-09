package games.uno.web;

import games.uno.GameService;
import games.uno.PlayerService;
import games.uno.domain.game.Player;
import games.uno.util.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/api/players")
@RestController
public class PlayerController {
    private static final String SESSION_ATTR = "HTTPSESSIONID";

    private final PlayerService playerService;
    private final GameService gameService;
    private final RandomDataGenerator generator;

    @Autowired
    public PlayerController(PlayerService playerService, GameService gameService, RandomDataGenerator generator) {
        this.playerService = playerService;
        this.gameService = gameService;
        this.generator = generator;
    }

    @SubscribeMapping("/game.players")
    public Collection<Player> retrievePlayers() {
        return playerService.findAll();
    }

    @SubscribeMapping("/game.cards")
    public Collection<PresentableCard> playerCards(StompHeaderAccessor accessor) {
        String sessionId = (String) accessor.getSessionAttributes().get(SESSION_ATTR);

        return playerService.find(sessionId).cardsOnHand().stream()
                .map(card -> PresentableCard.fromCard(card, gameService.currentCard()))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public Player create(@RequestParam(value = "username", defaultValue = "", required = false) String username,
                         @RequestParam(value = "password", required = false) String password,
                         HttpServletRequest request, Principal principal) {
        if (principal == null)
            return authorizeAndStore(generateUsernameIfEmpty(username), password, request.getSession().getId());
        else
            return playerService.find(request.getSession().getId());
    }

    private Player authorizeAndStore(String username, String password, String sessionId) {
        return playerService.save(sessionId, new Player(username));
    }

    public String generateUsernameIfEmpty(String username) {
        return (username.isEmpty()) ? generator.name() : username;
    }
}