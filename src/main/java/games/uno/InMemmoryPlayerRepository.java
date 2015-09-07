package games.uno;

import games.uno.domain.Player;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemmoryPlayerRepository implements PlayerRepository
{
    Map<String, Player> players = new HashMap<>();

    @Override
    public Player findBySessionId(String sessionId) {
        return players.get(sessionId);
    }

    @Override
    public Player saveForSession(String id, Player player) {
        players.put(id, player);

        return player;
    }

    @Override
    public List<Player> findAll() {
        return new ArrayList<>(players.values());
    }
}
