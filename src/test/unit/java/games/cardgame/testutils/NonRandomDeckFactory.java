package games.cardgame.testutils;

import games.cardgame.deck.Deck;
import games.uno.domain.cards.UnoCard;
import games.uno.domain.cards.UnoCardColors;
import games.uno.domain.cards.UnoCardValues;
import games.uno.util.RandomDeckFactory;

public class NonRandomDeckFactory extends RandomDeckFactory {
    public static final UnoCard ONE_RED = new UnoCard(UnoCardValues.ONE, UnoCardColors.RED);
    public static final UnoCard TWO_RED = new UnoCard(UnoCardValues.TWO, UnoCardColors.RED);
    public static final UnoCard THREE_RED = new UnoCard(UnoCardValues.THREE, UnoCardColors.RED);
    public static final UnoCard FOUR_RED = new UnoCard(UnoCardValues.FOUR, UnoCardColors.RED);
    public static final UnoCard FIVE_RED = new UnoCard(UnoCardValues.FIVE, UnoCardColors.RED);
    public static final UnoCard SIX_RED = new UnoCard(UnoCardValues.SIX, UnoCardColors.RED);
    public static final UnoCard SEVEN_RED = new UnoCard(UnoCardValues.SEVEN, UnoCardColors.RED);
    public static final UnoCard EIGHT_RED = new UnoCard(UnoCardValues.EIGHT, UnoCardColors.RED);
    public static final UnoCard NINE_RED = new UnoCard(UnoCardValues.NINE, UnoCardColors.RED);
    public static final UnoCard ZERO_RED = new UnoCard(UnoCardValues.ZERO, UnoCardColors.RED);

    public static final UnoCard REVERSE_RED = new UnoCard(UnoCardValues.REVERSE, UnoCardColors.RED);
    public static final UnoCard SKIP_RED = new UnoCard(UnoCardValues.SKIP, UnoCardColors.RED);
    public static final UnoCard DRAW_TWO_RED = new UnoCard(UnoCardValues.DRAW_TWO, UnoCardColors.RED);
    public static final UnoCard WILD_RED = new UnoCard(UnoCardValues.WILD, UnoCardColors.RED);
    public static final UnoCard WILD_DARK = new UnoCard(UnoCardValues.WILD, UnoCardColors.DARK);
    public static final UnoCard WILD_BLUE = new UnoCard(UnoCardValues.WILD, UnoCardColors.BLUE);
    public static final UnoCard WILD_DRAW_FOUR_DARK = new UnoCard(UnoCardValues.WILD_DRAW_FOUR, UnoCardColors.DARK);
    public static final UnoCard WILD_DRAW_FOUR_BLUE = new UnoCard(UnoCardValues.WILD_DRAW_FOUR, UnoCardColors.BLUE);

    public static final UnoCard ONE_BLUE = new UnoCard(UnoCardValues.ONE, UnoCardColors.BLUE);
    public static final UnoCard TWO_BLUE = new UnoCard(UnoCardValues.TWO, UnoCardColors.BLUE);
    public static final UnoCard THREE_BLUE = new UnoCard(UnoCardValues.THREE, UnoCardColors.BLUE);


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
