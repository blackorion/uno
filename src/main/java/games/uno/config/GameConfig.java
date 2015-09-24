package games.uno.config;

import games.uno.domain.game.*;
import games.uno.util.DeckFactory;
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
