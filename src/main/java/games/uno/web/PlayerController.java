package games.uno.web;

import games.uno.PlayerRepository;
import games.uno.domain.Player;
import games.uno.util.RandomUsernameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

@CrossOrigin
@RequestMapping("/api/players")
@RestController
public class PlayerController
{
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    RandomUsernameGenerator usernameGenerator;

    @SubscribeMapping("/game.players")
    public Collection<Player> retrievePlayers() {
        return playerRepository.findAll();
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public Player create(@RequestParam(value = "username", defaultValue = "", required = false) String username,
                         @RequestParam(value = "password", required = false) String password,
                         HttpServletRequest request, Principal principal) {
        if ( principal == null ) {
            username = generateUsernameIfEmpty(username);
            authorizePlayer(username, password);
            return savePlayerInHttpSession(username, request.getSession().getId());
        }
        else
            return playerRepository.findBySessionId(request.getSession().getId());
    }

    private LoginStatus authorizePlayer(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new LoginStatus(auth.isAuthenticated(), auth.getName());
    }

    private Player savePlayerInHttpSession(String username, String sessionId) {
        return playerRepository.saveForSession(sessionId, new Player(username));
    }

    private String generateUsernameIfEmpty(String username) {
        if ( username.isEmpty() )
            return usernameGenerator.generate();
        else
            return username;
    }

    private class LoginStatus
    {
        private final boolean loggedIn;
        private final String username;

        public LoginStatus(boolean loggedIn, String username) {
            this.loggedIn = loggedIn;
            this.username = username;
        }

        public boolean isLoggedIn() {
            return loggedIn;
        }

        public String getUsername() {
            return username;
        }
    }
}
