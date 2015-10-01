package games.uno.web.messages;

import games.cardgame.player.Player;
import games.uno.domain.cards.UnoCard;
import games.uno.domain.cards.UnoCardColors;
import games.uno.domain.cards.UnoCardValues;
import games.uno.services.GameService;

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

                gameService.playCard(player, new UnoCard(UnoCardValues.valueOf(card.get("value")), UnoCardColors.valueOf(card.get("color"))));
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
