package games.uno;

import games.cardgame.player.Player;
import games.uno.repositories.PlayerRepository;
import games.uno.services.PlayerService;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerServiceTest
{
    PlayerRepository mockRepository = mock(PlayerRepository.class);
    AuthenticationManager mockAuthentication = mock(AuthenticationManager.class);
    PlayerService service = new PlayerService(mockRepository, mockAuthentication);

    @Test
    public void WhenPlayerExists_ReturnsCurrentUser() {
        String session = "session-id";
        Player expected = new Player("player");
        when(mockRepository.findBySessionId(session)).thenReturn(expected);

        Player received = service.findOrNew(session);

        assertThat(received, is(expected));
    }
}