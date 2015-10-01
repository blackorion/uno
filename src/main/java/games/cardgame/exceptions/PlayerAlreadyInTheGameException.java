package games.cardgame.exceptions;

import games.cardgame.player.Player;

public class PlayerAlreadyInTheGameException extends RuntimeException
{
    private static final long serialVersionUID = 6002650183554889356L;

    public PlayerAlreadyInTheGameException(Player player) {
        super("Player already in the game: " + player.getName());
    }
}
