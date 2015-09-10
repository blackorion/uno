package games.uno;

import games.uno.domain.cards.Card;
import games.uno.domain.game.Player;
import games.uno.domain.game.UnoGameFacade;
import games.uno.util.DeckFactory;
import games.uno.web.messages.GameInfoMessage;
import games.uno.websockets.PlayerEventInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class GameService
{
    private final PlayerEventInformer informer;
    private final UnoGameFacade game;

    @Autowired
    public GameService(DeckFactory factory, PlayerEventInformer informer) {
        this.informer = informer;
        this.game = new UnoGameFacade(factory);
    }

    public void addPlayer(Player player) {
        game.addPlayer(player);
        informer.sendPlayersListToAll(game.players());
    }

    public void startNewGame() {
        game.start();
        informer.sendHandToAllPlayers(currentCard());
        informer.sendPlayersListToAll(game.players());
    }

    public void stopCurrentGame() {
        game.finish();
        informer.sendPlayersListToAll(game.players());
    }

    public Card currentCard() {
        return game.currentCard();
    }

    public void playCard(Player player, Card card) {
        if ( player != game.currentPlayer() )
            return;

        game.playerPlaysA(card);
        informer.sendHandToAllPlayers(currentCard());
        informer.sendPlayersListToAll(game.players());
    }

    public void drawCard(Player player) {
        if ( player != game.currentPlayer() )
            return;

        game.playerDrawsFromDeck();
        informer.sendPlayerHand(player, currentCard());
        informer.sendPlayersListToAll(game.players());
    }

    public GameInfoMessage getInfo() {
        return new GameInfoMessage(
                game.state(),
                game.playersSize(),
                game.currentPlayer().getId(),
                game.currentCard(),
                game.bankRemains(),
                game.getDirection()
        );
    }

    public Collection<Player> players() {
        return game.players();
    }
}