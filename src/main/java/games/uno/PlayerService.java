package games.uno;

import games.uno.domain.game.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PlayerService
{
    private static final String SESSION_ATTR = "HTTPSESSIONID";

    private PlayerRepository repository;
    private AuthenticationManager authenticationManager;

    @Autowired
    public PlayerService(PlayerRepository repository, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
    }

    public Player findOrNew(String sessionId) {
        return repository.findBySessionId(sessionId);
    }

    public Collection<Player> findAll() { return repository.findAll(); }

    public Player find(String sessionId) { return repository.findBySessionId(sessionId); }

    public Player find(StompHeaderAccessor accessor) {
        return find((String) accessor.getSessionAttributes().get(SESSION_ATTR));
    }

    public Player save(String sessionId, Player player) {
        return repository.saveForSession(sessionId, player);
    }

    public LoginStatus authorizePlayer(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new LoginStatus(auth.isAuthenticated(), auth.getName());
    }

    public void flushSession() {
        repository.deleteAll();
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
