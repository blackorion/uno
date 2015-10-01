package games.uno.config;

import games.cardgame.core.*;
import games.cardgame.deck.DeckFactory;
import games.uno.domain.game.UnoGameMaster;
import games.uno.domain.game.UnoRulesManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfig {
    @Bean
    public CardGame cardGame(GameMaster master, RulesManager manager) {
        return new BasicCardGame(master, manager);
    }

    @Bean
    public RulesManager rulesManager(GameMaster master, ScoreCounter scoreCounter) {
        return new UnoRulesManager(master, scoreCounter);
    }

    @Bean
    public GameMaster gameMaster(DeckFactory deckFactory) {
        return new UnoGameMaster(deckFactory);
    }

    @Bean
    public ScoreCounter scoreCounter(GameMaster gameMaster) {
        return new ScoreCounter(gameMaster.getTable());
    }
}
