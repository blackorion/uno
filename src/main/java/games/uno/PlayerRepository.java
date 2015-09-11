package games.uno;

import games.uno.domain.game.Player;

import java.util.List;

public interface PlayerRepository {
    Player findBySessionId(String sessionId);

    List<Player> findAll();

    Player saveForSession(String id, Player player);

    void deleteAll();
}
