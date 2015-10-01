package games.uno.services;

import games.cardgame.core.CardGame;
import games.cardgame.player.Player;
import games.uno.domain.cards.UnoCard;
import games.uno.web.messages.GameInfoMessage;
import games.uno.websockets.PlayerEventInformer;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GameService {
    private final PlayerEventInformer informer;
    private final CardGame game;

    @Autowired
    public GameService(CardGame game, PlayerEventInformer informer) {
        this.informer = informer;
        this.game = game;
    }

    public void addPlayer(Player player) {
        game.addPlayer(player);
        informer.sendPlayersListToAll(game.players());
    }

    public void startNewGame() {
        game.start();
        informer.sendHandToAllPlayers(currentCard(), lastDrawnCard());
        informer.sendPlayersListToAll(game.players());
    }

    public void stopCurrentGame() {
        game.finish();
        informer.sendPlayersListToAll(game.players());
    }

    public UnoCard currentCard() {
        return game.currentCard();
    }

    public void playCard(Player player, UnoCard card) {
        if (player != game.currentPlayer())
            return;

        game.playerPlaysA(card);
        informer.sendHandToAllPlayers(currentCard(), lastDrawnCard());
        informer.sendPlayersListToAll(game.players());
    }

    public void drawCard(Player player) {
        if (!player.equals(game.currentPlayer()))
            return;

        UnoCard card = game.playerDrawsFromDeck();
        informer.sendPlayerHand(player, card, currentCard());
        informer.sendPlayersListToAll(game.players());
    }

    public void endTurn() {
        Player previousPlayer = game.currentPlayer();
        game.endTurn();
        informer.sendPlayersListToAll(game.players());
        informer.sendPlayerHand(previousPlayer, null, currentCard());
        informer.sendPlayerHand(game.currentPlayer(), null, currentCard());
    }

    public Collection<Player> players() {
        return game.players();
    }

    public UnoCard getLastDrawnCardBy(Player player) {
        if (game.currentPlayer().equals(player))
            return game.lastDrawnCard();
        else
            return null;
    }

    public void removeAllPlayers() {
        game.removePlayers();
    }

    private Pair<Player, UnoCard> lastDrawnCard() {
        return new Pair<>(game.currentPlayer(), game.lastDrawnCard());
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
}