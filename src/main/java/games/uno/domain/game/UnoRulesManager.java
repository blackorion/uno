package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardValues;
import games.uno.exceptions.WrongMoveException;

public class UnoRulesManager implements RulesManager
{
    private final GameMaster game;

    public UnoRulesManager(GameMaster game) {
        this.game = game;
    }

    @Override
    public void gameStarted() {
        game.start();
        eachPlayerGetsHand();
        game.flipACard();
    }

    @Override
    public void cardPlayed(Card card) {
        if ( !card.isPlayable(game.currentPlayedCard()) )
            throw new WrongMoveException(game.currentPlayedCard(), card);

        game.playA(card);
        handleCardAction(card);
        game.setPlayerFinishedMove();
        game.endTurn();
    }

    private void handleCardAction(Card card) {
        if ( card.getValue() == CardValues.REVERSE )
            game.changeDirection();
        else if ( card.getValue() == CardValues.SKIP )
            game.endTurn();
        else if ( card.getValue() == CardValues.DRAW_TWO ) {
            game.endTurn();
            game.drawCard();
            game.drawCard();
        }
    }

    @Override
    public void gameStopped() {
        game.stop();
        game.flush();
    }

    @Override
    public void playerDraws() {
        game.drawCard();
        game.setPlayerFinishedMove();
    }

    private void eachPlayerGetsHand() {
        game.eachPlayer(this::drawSevenCards);
    }

    private Player drawSevenCards(Player player) {
        for ( int j = 0; j < 7; j++ )
            game.playerDrawsFromDeck();

        game.endTurn();

        return player;
    }
}
