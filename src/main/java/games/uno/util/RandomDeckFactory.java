package games.uno.util;

import games.uno.domain.cards.DeckBuilder;
import games.uno.domain.cards.CardColors;
import games.uno.domain.cards.CardTypes;
import games.uno.domain.cards.CardValues;
import games.uno.domain.cards.Deck;
import org.springframework.stereotype.Component;

@Component
public class RandomDeckFactory implements DeckFactory
{
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
        for ( CardColors color : CardColors.values() )
            if ( color != CardColors.DARK )
                for ( CardValues value : CardValues.values() )
                    if ( value.getType() == CardTypes.NUMERIC && value != CardValues.ZERO )
                        for ( int i = 0; i < 2; i++ )
                            builder.add(value, color);

        for ( CardColors color : CardColors.values() )
            if ( color != CardColors.DARK )
                builder.add(CardValues.ZERO, color);
    }

    private void addActionCards() {
        for ( CardColors color : CardColors.values() )
            if ( color != CardColors.DARK )
                for ( int i = 0; i < 2; i++ ) {
                    builder.add(CardValues.DRAW_TWO, color);
                    builder.add(CardValues.REVERSE, color);
                    builder.add(CardValues.SKIP, color);
                }


        for ( int i = 0; i < 4; i++ ) {
            builder.add(CardValues.WILD, CardColors.DARK);
            builder.add(CardValues.WILD_DRAW_FOUR, CardColors.DARK);
        }
    }
}
