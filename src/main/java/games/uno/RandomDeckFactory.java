package games.uno;

import games.uno.domain.CardColors;
import games.uno.domain.CardTypes;
import games.uno.domain.CardValues;
import games.uno.domain.Deck;
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
                    builder.add(CardValues.TAKE_TWO, color);
                    builder.add(CardValues.ONE_STEP_BACK, color);
                    builder.add(CardValues.PASS_TURN, color);
                }


        for ( int i = 0; i < 4; i++ ) {
            builder.add(CardValues.PICK_COLOR, CardColors.DARK);
            builder.add(CardValues.TAKE_FOUR, CardColors.DARK);
        }
    }
}
