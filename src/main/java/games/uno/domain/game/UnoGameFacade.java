package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.Deck;
import games.uno.exceptions.NoUsersInTheGameException;
import games.uno.util.DeckFactory;

import java.util.List;

public class UnoGameFacade implements CardGame
{
    private final RulesManager rulesManager;
    private final GameTable table;
    private final UnoGameMaster master;

    public UnoGameFacade(DeckFactory deckFactory) {
        table = new GameTable(this);
        master = new UnoGameMaster(deckFactory.generate(), new Deck(), table);
        rulesManager = new UnoRulesManager(master);
    }

    @Override
    public void addPlayer(Player player) {
        table.add(player);
    }

    @Override
    public void start() {
        if ( !hasPlayers() )
            throw new NoUsersInTheGameException();

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
        return table.currentPlayer();
    }

    @Override
    public void playerDrawsFromDeck() {
        rulesManager.playerDraws();
    }

    @Override
    public int playersSize() {
        return table.players().size();
    }

    public int bankRemains() {
        return master.deckRemains();
    }

    public List<Player> players() {
        return table.players();
    }

    public GameState state() {
        return master.state();
    }

    public BidirectionalQueue.Direction getDirection() {
        return table.getDirection();
    }

    @Override
    public boolean hasPlayers() {
        return !table.isEmpty();
    }


    @Override
    public void playerPlaysA(Card card) {
        rulesManager.cardPlayed(card);
    }
}
