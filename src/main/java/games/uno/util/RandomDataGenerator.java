package games.uno.util;

import io.codearte.jfairy.Fairy;
import org.springframework.stereotype.Component;

@Component
public class RandomDataGenerator
{
    private Fairy generator = Fairy.create();

    public String name() {
        return generator.person().fullName();
    }

    public String login() {
        return generator.person().middleName();
    }
}
