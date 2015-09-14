package games.uno.util;

import games.uno.domain.cards.*;
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
        nonDarkColors().forEach(color -> builder.add(CardValues.ZERO, color));
    }

    private void addColoredActionCards() {
        nonDarkColors().forEach(color -> {
            for (int i = 0; i < 2; i++) {
                builder.add(CardValues.DRAW_TWO, color);
                builder.add(CardValues.REVERSE, color);
                builder.add(CardValues.SKIP, color);
            }
        });
    }

    private void addWildCards() {
        for (int i = 0; i < 4; i++) {
            builder.add(CardValues.WILD, CardColors.DARK);
            builder.add(CardValues.WILD_DRAW_FOUR, CardColors.DARK);
        }
    }

    private List<CardColors> nonDarkColors() {
        return Arrays.asList(CardColors.values())
                .stream()
                .filter(cardColors -> cardColors != CardColors.DARK)
                .collect(Collectors.toList());
    }

    private List<CardValues> numericNonZeroValues() {
        return Arrays.asList(CardValues.values())
                .stream()
                .filter(value -> value.getType() == CardTypes.NUMERIC && value != CardValues.ZERO)
                .collect(Collectors.toList());
    }
}
