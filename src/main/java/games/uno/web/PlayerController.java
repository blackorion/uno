package games.uno.web;

import games.uno.Player;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin
@RequestMapping("/api/players")
public class PlayerController
{
    Fairy generator = Fairy.create();

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Player player(@RequestParam(value = "username", required = false) String username, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Player player = (Player) session.getAttribute("player");

        if ( player == null ) {
            player = populatePlayer(username);
            session.setAttribute("player", player);
        }

        return player;
    }

    public Player populatePlayer(String username) {
        if ( username == null )
            username = generateUsername();

        return new Player(username);
    }

    private String generateUsername() {
        Person person = generator.person();

        return person.fullName();
    }
}
