package games.uno.domain.game.cardplaystrategies;

import games.cardgame.core.GameMaster;
import games.cardgame.core.ScoreCounter;
import games.uno.domain.cards.UnoCard;
import games.uno.domain.game.UnoRulesManager;

public class BasicCardPlayStrategy extends CardPlayStrategy {
    private final ScoreCounter scoreCounter;

    public BasicCardPlayStrategy(UnoRulesManager rulesManager, GameMaster game, ScoreCounter scoreCounter) {
        super(rulesManager, game);
        this.scoreCounter = scoreCounter;
    }

    @Override
    public void play(UnoCard card) {
        verifyMove(card);
        doPlayCard(card);
        finishTurn();
    }

    private void finishTurn() {
        game.setPlayerFinishedMove();

        if (isLastCard()) {
            game.finishRound();
            computeScore();

            if (game.currentPlayer().getGameScore() >= 500)
                game.stop();

            return ;
        }

        rulesManager.endTurn();
    }

    private boolean isLastCard() {
        return game.currentPlayer().cardsOnHand().isEmpty();
    }

    private void computeScore() {
        scoreCounter.compute();
    }
}
