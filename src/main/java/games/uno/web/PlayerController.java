package games.uno.web;

import games.uno.Player;
import games.uno.PlayerRepository;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin
@RequestMapping("/api/players")
public class PlayerController
{
    private Fairy generator = Fairy.create();
    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Player create(@RequestParam(value = "username", required = false) String username, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Player player = populatePlayer(username);
        playerRepository.saveForSession(session.getId(), player);

        return player;
    }

    public Player populatePlayer(String username) {
        if ( username == null )
            username = generateUsername();

        return new Player(username);
    }

    private String generateUsername() {
        return generator.person().fullName();
    }
}
