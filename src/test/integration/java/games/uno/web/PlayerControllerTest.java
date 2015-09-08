package games.uno.web;

import games.uno.GameService;
import games.uno.PlayerService;
import games.uno.domain.Player;
import games.uno.util.RandomDataGenerator;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.Assert.assertNotNull;
import javax.servlet.http.HttpSession;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlayerControllerTest
{
    PlayerController controller;
    MockMvc mvc;

    @Before
    public void setUp() {
        controller = new PlayerController(Mockito.mock(PlayerService.class), Mockito.mock(GameService.class), Mockito.mock(RandomDataGenerator.class));
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldStorePlayerObjectInSession() throws Exception {
        MvcResult result = mvc.perform(get("/api/players").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        HttpSession session = result.getRequest().getSession();

        assertNotNull(session.getAttribute("player"));
    }

    @Test
    public void shouldCreatePlayerWithGeneratedName() throws Exception {
        mvc.perform(get("/api/players").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(IsNull.notNullValue()));
    }

    @Test
    public void shouldSetTheTransferredNameToPlayerIfCreated() throws Exception {
        mvc.perform(get("/api/players").param("username", "username").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(is("username")));
    }

    @Test
    public void shouldFetchPlayerFromSessionIfExists() throws Exception {
        Player player = new Player("username");

        mvc.perform(get("/api/players")
                .param("username", "other name")
                .sessionAttr("player", player)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(is("username")));
    }
}