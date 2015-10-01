package games.cardgame.core;

import games.cardgame.exceptions.WonPlayerDetermineException;
import games.cardgame.player.Player;
import games.uno.domain.cards.UnoCard;

public class ScoreCounter {
    private final GameTable table;

    public ScoreCounter(GameTable table) {
        this.table = table;
    }

    public void compute() {
        final Player[] won = {null};
        final Integer[] total = {0};

        table.players().parallelStream().forEach(player -> {
            int score = calculateScoreFor(player);
            total[0] += score;

            if (score == 0)
                won[0] = player;
        });

        if (won[0] == null)
            throw new WonPlayerDetermineException();

        won[0].addScore(total[0]);
    }

    private int calculateScoreFor(Player player) {
        int result = 0;

        for (UnoCard card : player.cardsOnHand())
            result += card.getScore();

        return result;
    }
}
