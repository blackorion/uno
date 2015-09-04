package games.uno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService
{
    Uno game;

    @Autowired
    public GameService(DeckFactory factory) {
        this.game = new Uno(factory);
    }

    public Uno fetchGame() {
        return game;
    }
}
