package games.uno.domain.game;

import games.uno.domain.cards.Card;

public class BasicCardPlayStrategy extends CardPlayStrategy {
    private final ScoreCounter scoreCounter;

    public BasicCardPlayStrategy(UnoRulesManager rulesManager, GameMaster game, ScoreCounter scoreCounter) {
        super(rulesManager, game);
        this.scoreCounter = scoreCounter;
    }

    @Override
    public void play(Card card) {
        verifyMove(card);
        doPlayCard(card);
        finishTurn();
    }

    private void finishTurn() {
        game.setPlayerFinishedMove();

        if (game.currentPlayer().cardsOnHand().size() == 0) {
            game.finishRound();
            computeScore();

            if (game.currentPlayer().getGameScore() >= 500)
                game.stop();
        }

        rulesManager.endTurn();
    }

    private void computeScore() {
        scoreCounter.compute();
    }
}
