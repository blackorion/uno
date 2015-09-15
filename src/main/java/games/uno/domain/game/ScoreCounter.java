package games.uno.domain.game;

import games.uno.domain.cards.Card;
import games.uno.exceptions.WonPlayerDetermineException;

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

        for (Card card : player.cardsOnHand())
            result += card.getScore();

        return result;
    }
}
