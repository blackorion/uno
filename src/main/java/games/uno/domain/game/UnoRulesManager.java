package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.exceptions.WrongMoveException;

public class UnoRulesManager implements RulesManager {
    private final CardGame game;

    public UnoRulesManager(CardGame game) {
        this.game = game;
    }

    @Override
    public void gameStarted() {
        eachPlayerGetsHand();
        game.flipACard();
    }

    @Override
    public void cardPlayed(Card card) {
        if (!card.isPlayable(game.currentPlayedCard()))
            throw new WrongMoveException(game.currentPlayedCard(), card);

        game.getPlayDeck().takeCardFrom(game.getCurrentPlayer(), card);
        game.getCurrentPlayer().finishedHisMove();
        game.endTurn();
    }

    private void eachPlayerGetsHand() {
        for (int i = 0; i < game.playersSize(); i++) {
            drawSevenCards();
            game.endTurn();
        }
    }

    private void drawSevenCards() {
        for (int j = 0; j < 7; j++)
            game.playerDrawsFromDeck();
    }
}
