package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardValues;
import games.uno.exceptions.IllegalTurnEndException;
import games.uno.exceptions.WrongMoveException;

public class UnoRulesManager implements RulesManager {
    private final GameMaster game;
    private CardPlayStrategy cardPlayStrategy;
    private ScoreCounter scoreCounter;

    public UnoRulesManager(GameMaster game, ScoreCounter scoreCounter) {
        this.game = game;
        this.scoreCounter = scoreCounter;
        setDefaultCardPlayStrategy();
    }

    void setDefaultCardPlayStrategy() {
        this.cardPlayStrategy = new BasicCardPlayStrategy(this, game, scoreCounter);
    }

    void setFirstCardWildPlayStrategy() {
        this.cardPlayStrategy = new FirstCardWildPlayStrategy(this, game);
    }

    @Override
    public void gameStarted() {
        game.start();
        game.giveEachPlayerCards(7);
        handleTopCardAction();
        game.flipACard();
        game.persuadePlayerToPlay();
    }

    private void handleTopCardAction() {
        while (game.deckFirstCardToDraw().getValue() == CardValues.WILD_DRAW_FOUR)
            game.shuffleDeck();

        if (game.deckFirstCardToDraw().getValue() == CardValues.WILD) {
            setFirstCardWildPlayStrategy();
            game.drawCard();
        }

        game.deckFirstCardToDraw().applyAction(game);

        if (game.deckFirstCardToDraw().getValue() == CardValues.REVERSE)
            game.nextPlayer();
    }

    @Override
    public void cardPlayed(Card card) {
        cardPlayStrategy.play(card);
    }

    @Override
    public void gameStopped() {
        game.stop();
        game.flushDeckAndPill();
        game.flushPlayersHand();
    }

    @Override
    public Card playerDraws() {
        if (game.didPlayerDrewThisTurn())
            throw new WrongMoveException("Can't draw more than one card in turn.");

        Card lastDrawnCard = game.drawCard();
        game.setPlayerFinishedMove();

        if (game.deckIsEmpty())
            game.updateDeckFromPill();

        if (drawnCardNotPlayable(lastDrawnCard))
            endTurn();

        return game.lastDrawnCard();
    }

    private boolean drawnCardNotPlayable(Card card) {
        return card != null && !card.isPlayable(game.currentPlayedCard());
    }

    @Override
    public void endTurn() {
        if (game.isPlayerShouldPlay())
            throw new IllegalTurnEndException();

        game.nextPlayer();
        game.persuadePlayerToPlay();
    }
}
