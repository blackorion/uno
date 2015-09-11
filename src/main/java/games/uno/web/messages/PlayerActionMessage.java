package games.uno.web.messages;

import games.uno.GameService;
import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;
import games.uno.domain.game.Player;

import java.util.Map;

public class PlayerActionMessage {
    public PlayerActions action;
    public Map<String, Object> data;

    public void invoke(Player player, GameService gameService) {
        action.invoke(this, player, gameService);
    }

    private enum PlayerActions {
        DRAW_CARD {
            @Override
            public void invoke(PlayerActionMessage event, Player player, GameService gameService) {
                gameService.drawCard(player);
            }
        },

        PLAY_CARD {
            @Override
            public void invoke(PlayerActionMessage event, Player player, GameService gameService) {
                if (!event.data.containsKey("card"))
                    throw new IllegalArgumentException("No card data passed");

                Map<String, String> card = (Map<String, String>) event.data.get("card");

                gameService.playCard(player, new Card(CardValues.valueOf(card.get("value")), CardColors.valueOf(card.get("color"))));
            }
        },
        END_TURN {
            @Override
            public void invoke(PlayerActionMessage event, Player player, GameService gameService) {
                gameService.endTurn();
            }
        },
        CHANGE_NAME {
            @Override
            public void invoke(PlayerActionMessage event, Player player, GameService gameService) {
                String name = (String) event.data.get("name");
                player.setName(name);
            }
        };

        abstract public void invoke(PlayerActionMessage event, Player player, GameService gameService);
    }
}
