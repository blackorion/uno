package games.uno.repositories;

import games.cardgame.player.Player;

import java.util.List;

public interface PlayerRepository {
    Player findBySessionId(String sessionId);

    List<Player> findAll();

    Player saveForSession(String id, Player player);

    void deleteAll();
}
