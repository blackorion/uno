package games.uno.web;

import games.uno.web.messages.Greeting;
import games.uno.web.messages.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController
{
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(3000);
        return new Greeting("Hello, " + message.getName() + "!");
    }
}

