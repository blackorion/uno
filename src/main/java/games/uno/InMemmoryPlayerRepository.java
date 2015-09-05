package games.uno;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemmoryPlayerRepository implements PlayerRepository {
    Map<String, Player> players = new HashMap<>();

    @Override
    public Player findBySessionId(String sessionId) {
        return players.get(sessionId);
    }

    @Override
    public void saveForSession(String id, Player player) {
        players.put(id, player);
    }
}
