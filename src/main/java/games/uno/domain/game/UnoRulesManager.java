package games.uno.domain.game;

import games.cardgame.core.GameMaster;
import games.cardgame.core.RulesManager;
import games.cardgame.core.ScoreCounter;
import games.cardgame.exceptions.IllegalTurnEndException;
import games.cardgame.exceptions.WrongMoveException;
import games.uno.domain.cards.UnoCard;
import games.uno.domain.cards.UnoCardValues;
import games.uno.domain.game.cardplaystrategies.BasicCardPlayStrategy;
import games.uno.domain.game.cardplaystrategies.CardPlayStrategy;
import games.uno.domain.game.cardplaystrategies.FirstCardWildPlayStrategy;

public class UnoRulesManager implements RulesManager {
    private final GameMaster game;
    private CardPlayStrategy cardPlayStrategy;
    private ScoreCounter scoreCounter;

    public UnoRulesManager(GameMaster game, ScoreCounter scoreCounter) {
        this.game = game;
        this.scoreCounter = scoreCounter;
        setDefaultCardPlayStrategy();
    }

    public void setDefaultCardPlayStrategy() {
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
        while (game.deckFirstCardToDraw().getValue() == UnoCardValues.WILD_DRAW_FOUR)
            game.shuffleDeck();

        if (game.deckFirstCardToDraw().getValue() == UnoCardValues.WILD) {
            setFirstCardWildPlayStrategy();
            game.drawCard();
        }

        game.deckFirstCardToDraw().applyAction(game);

        if (game.deckFirstCardToDraw().getValue() == UnoCardValues.REVERSE)
            game.nextPlayer();
    }

    @Override
    public void cardPlayed(UnoCard card) {
        cardPlayStrategy.play(card);
    }

    @Override
    public void gameStopped() {
        game.stop();
        game.flushDeckAndPill();
        game.flushPlayersHand();
    }

    @Override
    public UnoCard playerDraws() {
        if (game.didPlayerDrewThisTurn())
            throw new WrongMoveException("Can't draw more than one card in turn.");

        UnoCard lastDrawnCard = game.drawCard();
        game.setPlayerFinishedMove();

        if (drawnCardNotPlayable(lastDrawnCard))
            endTurn();

        return game.lastDrawnCard();
    }

    private boolean drawnCardNotPlayable(UnoCard card) {
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
