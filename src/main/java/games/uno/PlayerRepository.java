package games.uno;

import games.uno.domain.Player;
import java.util.List;

public interface PlayerRepository {
    Player findBySessionId(String sessionId);

    List<Player> findAll();

    Player saveForSession(String id, Player player);
}
