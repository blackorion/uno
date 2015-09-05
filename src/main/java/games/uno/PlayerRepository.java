package games.uno;

public interface PlayerRepository {
    Player findBySessionId(String sessionId);

    void saveForSession(String id, Player player);
}
