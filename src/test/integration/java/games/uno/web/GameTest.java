package games.uno.web;

import games.uno.UnoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UnoApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8080")
public class GameTest {
    Game game = new Game();

    @Test
    public void WhenFirstPlayerEnterTheSite_JoinsNewCreatedGame() {
        game.connect();
    }

    private class Game {
        private static final String URL = "ws://localhost:8080/ws";

        public void connect() {
            WebSocketClient transport = new StandardWebSocketClient();
            WebSocketStompClient stompClient = new WebSocketStompClient(transport);
            stompClient.setMessageConverter(new StringMessageConverter());
            stompClient.connect(URL, makeSessionHandler());
        }
    }

    private StompSessionHandler makeSessionHandler() {
        return new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/game.players", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders stompHeaders) {
                        return String.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders stompHeaders, Object o) {
                        System.out.println(o);
                    }
                });
            }
        };
    }
}
