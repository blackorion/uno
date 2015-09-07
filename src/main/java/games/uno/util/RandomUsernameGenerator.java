package games.uno.util;

import io.codearte.jfairy.Fairy;
import org.springframework.stereotype.Component;

@Component
public class RandomUsernameGenerator
{
    private Fairy generator = Fairy.create();

    public String generate() {
        return generator.person().fullName();
    }
}
