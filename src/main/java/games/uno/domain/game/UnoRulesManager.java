package games.uno.domain.game;

public class UnoRulesManager implements RulesManager
{
    private final Uno game;

    public UnoRulesManager(Uno game) {
        this.game = game;
    }

    @Override
    public void gameStarted() {
        eachPlayerGetsHand();
    }

    private void eachPlayerGetsHand() {
        for ( int i = 0; i < 7; i++ )
            game.playerDrawsFromDeck();
    }
}
