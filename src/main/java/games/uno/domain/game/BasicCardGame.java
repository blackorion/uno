package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.exceptions.MinimumUsersForTheGameException;

import java.util.List;

public class BasicCardGame implements CardGame {
    private final RulesManager rulesManager;
    private final GameMaster master;

    public BasicCardGame(GameMaster master, RulesManager manager) {
        this.master = master;
        this.rulesManager = manager;
    }

    @Override
    public void addPlayer(Player player) {
        master.getTable().add(player);
    }

    @Override
    public void start() {
        if (playersSize() < 2)
            throw new MinimumUsersForTheGameException(playersSize());

        rulesManager.gameStarted();
    }

    @Override
    public void finish() {
        rulesManager.gameStopped();
    }

    @Override
    public void endTurn() {
        rulesManager.endTurn();
    }

    @Override
    public Card currentCard() {
        return master.currentPlayedCard();
    }

    @Override
    public Player currentPlayer() {
        return master.currentPlayer();
    }

    @Override
    public void playerPlaysA(Card card) {
        rulesManager.cardPlayed(card);
    }

    @Override
    public Card playerDrawsFromDeck() {
        return rulesManager.playerDraws();
    }

    @Override
    public int bankRemains() {
        return master.deckRemains();
    }

    @Override
    public List<Player> players() {
        return master.getTable().players();
    }

    @Override
    public int playersSize() {
        return players().size();
    }

    @Override
    public BidirectionalQueue.Direction getDirection() {
        return master.getTable().getDirection();
    }

    @Override
    public GameState state() {
        return master.state();
    }

    @Override
    public Card lastDrawnCard() {
        return master.lastDrawnCard();
    }
}
