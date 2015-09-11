package games.uno.testutils;

import games.uno.domain.cards.Card;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardValues;
import games.uno.domain.cards.Deck;
import games.uno.util.RandomDeckFactory;

public class NonRandomDeckFactory extends RandomDeckFactory {
    public static final Card ONE_RED = new Card(CardValues.ONE, CardColors.RED);
    public static final Card TWO_RED = new Card(CardValues.TWO, CardColors.RED);
    public static final Card THREE_RED = new Card(CardValues.THREE, CardColors.RED);
    public static final Card FOUR_RED = new Card(CardValues.FOUR, CardColors.RED);
    public static final Card FIVE_RED = new Card(CardValues.FIVE, CardColors.RED);
    public static final Card SIX_RED = new Card(CardValues.SIX, CardColors.RED);
    public static final Card SEVEN_RED = new Card(CardValues.SEVEN, CardColors.RED);
    public static final Card EIGHT_RED = new Card(CardValues.EIGHT, CardColors.RED);
    public static final Card NINE_RED = new Card(CardValues.NINE, CardColors.RED);
    public static final Card ZERO_RED = new Card(CardValues.ZERO, CardColors.RED);

    public static final Card REVERSE_RED = new Card(CardValues.REVERSE, CardColors.RED);
    public static final Card SKIP_RED = new Card(CardValues.SKIP, CardColors.RED);
    public static final Card DRAW_TWO_RED = new Card(CardValues.DRAW_TWO, CardColors.RED);
    public static final Card WILD_RED = new Card(CardValues.WILD, CardColors.RED);
    public static final Card WILD_DARK = new Card(CardValues.WILD, CardColors.DARK);
    public static final Card WILD_BLUE = new Card(CardValues.WILD, CardColors.BLUE);
    public static final Card WILD_DRAW_FOUR_DARK = new Card(CardValues.WILD_DRAW_FOUR, CardColors.DARK);
    public static final Card WILD_DRAW_FOUR_BLUE = new Card(CardValues.WILD_DRAW_FOUR, CardColors.BLUE);

    public static final Card ONE_BLUE = new Card(CardValues.ONE, CardColors.BLUE);
    public static final Card TWO_BLUE = new Card(CardValues.TWO, CardColors.BLUE);
    public static final Card THREE_BLUE = new Card(CardValues.THREE, CardColors.BLUE);


    @Override
    public Deck generate() {
        initCards();
        return builder.build();
    }

    @Override
    protected void initCards() {
        builder
                .add(ONE_RED)
                .add(TWO_RED)
                .add(THREE_RED)
                .add(FOUR_RED)
                .add(FIVE_RED)
                .add(SIX_RED)
                .add(SEVEN_RED)
                .add(EIGHT_RED)
                .add(NINE_RED)
                .add(ONE_RED)
                .add(TWO_RED)
                .add(THREE_RED)
                .add(FOUR_RED)
                .add(FIVE_RED)
                .add(SIX_RED)
                .add(SEVEN_RED)
                .add(EIGHT_RED)
                .add(NINE_RED)
                .add(ZERO_RED);
    }

}
