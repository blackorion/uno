package games.uno;

import games.uno.domain.game.Player;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemmoryPlayerRepository implements PlayerRepository
{
    private AtomicLong counter = new AtomicLong();
    private Map<String, Player> players = new HashMap<>();

    @Override
    public Player findBySessionId(String sessionId) {
        return players.get(sessionId);
    }

    @Override
    public Player saveForSession(String id, Player player) {
        player.setId(counter.getAndIncrement());
        players.put(id, player);

        return player;
    }

    @Override
    public List<Player> findAll() {
        return new ArrayList<>(players.values());
    }
}
