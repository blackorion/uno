package games.uno.util;

import games.cardgame.deck.Deck;
import games.cardgame.deck.DeckBuilder;
import games.cardgame.deck.DeckFactory;
import games.uno.domain.cards.UnoCardColors;
import games.uno.domain.cards.UnoCardTypes;
import games.uno.domain.cards.UnoCardValues;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Priority(1)
public class RandomDeckFactory implements DeckFactory {
    protected DeckBuilder builder = new DeckBuilder();

    public Deck generate() {
        initCards();
        Deck deck = builder.build();
        deck.shuffle();

        return deck;
    }

    protected void initCards() {
        addNumericCards();
        addActionCards();
    }

    private void addNumericCards() {
        addOneToNineCards();
        addZeroCards();
    }

    private void addActionCards() {
        addColoredActionCards();
        addWildCards();
    }

    private void addOneToNineCards() {
        nonDarkColors().forEach(color ->
                        numericNonZeroValues().forEach(value -> {
                            for (int i = 0; i < 2; i++)
                                builder.add(value, color);
                        })
        );
    }

    private void addZeroCards() {
        nonDarkColors().forEach(color -> builder.add(UnoCardValues.ZERO, color));
    }

    private void addColoredActionCards() {
        nonDarkColors().forEach(color -> {
            for (int i = 0; i < 2; i++) {
                builder.add(UnoCardValues.DRAW_TWO, color);
                builder.add(UnoCardValues.REVERSE, color);
                builder.add(UnoCardValues.SKIP, color);
            }
        });
    }

    private void addWildCards() {
        for (int i = 0; i < 4; i++) {
            builder.add(UnoCardValues.WILD, UnoCardColors.DARK);
            builder.add(UnoCardValues.WILD_DRAW_FOUR, UnoCardColors.DARK);
        }
    }

    private List<UnoCardColors> nonDarkColors() {
        return Arrays.asList(UnoCardColors.values())
                .stream()
                .filter(cardColors -> cardColors != UnoCardColors.DARK)
                .collect(Collectors.toList());
    }

    private List<UnoCardValues> numericNonZeroValues() {
        return Arrays.asList(UnoCardValues.values())
                .stream()
                .filter(value -> value.getType() == UnoCardTypes.NUMERIC && value != UnoCardValues.ZERO)
                .collect(Collectors.toList());
    }
}
