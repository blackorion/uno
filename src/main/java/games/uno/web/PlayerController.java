package games.uno.web;

import games.uno.PlayerService;
import games.uno.domain.Card;
import games.uno.domain.Player;
import games.uno.util.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

@CrossOrigin
@RequestMapping("/api/players")
@RestController
public class PlayerController
{
    private static final String SESSION_ATTR = "HTTPSESSIONID";

    private final PlayerService service;
    private final RandomDataGenerator generator;

    @Autowired
    public PlayerController(PlayerService service, RandomDataGenerator generator) {
        this.service = service;
        this.generator = generator;
    }

    @SubscribeMapping("/game.players")
    public Collection<Player> retrievePlayers() { return service.findAll(); }

    @SubscribeMapping("/game.cards")
    public Collection<Card> playerCards(StompHeaderAccessor accessor) {
        Object o = accessor.getSessionAttributes().get(SESSION_ATTR);

        return service.find((String) o).cardsOnHand();
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public Player create(@RequestParam(value = "username", defaultValue = "", required = false) String username,
                         @RequestParam(value = "password", required = false) String password,
                         HttpServletRequest request, Principal principal) {
        if ( principal == null )
            return authorizeAndStore(generateUsernameIfEmpty(username), password, request.getSession().getId());
        else
            return service.find(request.getSession().getId());
    }

    private Player authorizeAndStore(String username, String password, String sessionId) {
        return service.save(sessionId, new Player(username));
    }

    public String generateUsernameIfEmpty(String username) {
        return (username.isEmpty()) ? generator.name() : username;
    }
}