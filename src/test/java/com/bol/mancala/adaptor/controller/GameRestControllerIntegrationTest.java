package com.bol.mancala.adaptor.controller;

import com.bol.mancala.adaptor.repository.GameRespository;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.GameManagement;
import com.bol.mancala.usecase.GameManagementImpl;
import com.bol.mancala.usecase.GamePlay;
import com.bol.mancala.usecase.GamePlayImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = GameRestController.class)
@Import({GamePlayImpl.class, GameManagementImpl.class})
class GameRestControllerIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(GameRestControllerIntegrationTest.class);

    private static final String STORE_ID = "8c9c8b74-f4b5-4ed1-b2dc-a48f8633b33a";

    private static final String STORE_BASE_URL = "/api/game";

    String alice = "Alice";
    String bob = "Bob";

    private String gamesListResponse;
    private Game game;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GameRespository gameRespository;
    @Autowired
    private GameManagement gameManagement;
    @Autowired
    private GamePlay gamePlay;

    @BeforeEach
    void setUp() {

//        gamePlay = new GamePlayImpl(gameManagement);
        ArrayList<Game> gameList = new ArrayList<>();
        game = new Game();
        game.setGameId(STORE_ID);
        game.setGamePlayable(true);
        game.getGameBoardMap().put(bob, new GameBoard(bob));
        game.getGameBoardMap().put(alice, new GameBoard(alice));
        gameList.add(game);

        ObjectMapper mapper = new ObjectMapper();
        try {
            gamesListResponse = mapper.writeValueAsString(gameList);
        } catch (JsonProcessingException e) {
            log.error("error while converting Stores to JSON", e);
        }
        Mockito.when(gameRespository.findById(anyString())).thenReturn(Optional.of(game));
        Mockito.when(gameRespository.findAll()).thenReturn(gameList);
        Mockito.when(gameRespository.save(any(Game.class))).thenAnswer((invocation) -> invocation.getArguments()[0]);


    }

    @Test
    void getAllGames() throws Exception {
        mockMvc.perform(get(STORE_BASE_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(gamesListResponse));
    }

    @Test
    void getGame() throws Exception {
        mockMvc.perform(get(STORE_BASE_URL + "/" + STORE_ID)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(STORE_ID));
    }

    @Test
    void createAndJoinGame() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("player1Name", alice);
        params.add("player2Name", bob);
        mockMvc.perform(post(STORE_BASE_URL).queryParams(params)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPlayerName").value(alice));
    }

    @Test
    void startGame() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("playerName", alice);
        mockMvc.perform(patch(STORE_BASE_URL + "/" + STORE_ID + "/start").queryParams(params)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameInProgress").value(true))
                .andExpect(jsonPath("$.currentPlayerName").value(alice));

    }

    @Test
    void play() throws Exception {

        String playResponse = createResponseJson();

        game.setGameInProgress(true);
        game.setCurrentPlayerName(alice);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("playerName", alice);
        params.add("index", "3");
        mockMvc.perform(patch(STORE_BASE_URL + "/" + STORE_ID + "/play").queryParams(params)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(playResponse));

    }

    private String createResponseJson() {
        String playResponse = "";
        GameBoard bobBoard = new GameBoard(bob);
        bobBoard.setBoard(new int[]{7,7,6,6,6,6});
        GameBoard aliceBoard = new GameBoard(alice);
        aliceBoard.setBoard(new int[]{6,6,0,7,7,7});
        aliceBoard.addToMancala(1);
        Game responseGame = new Game();
        responseGame.setGameId(game.getGameId());
        responseGame.setGameInProgress(true);
        responseGame.setGamePlayable(true);
        responseGame.setCurrentPlayerName(bob);
        responseGame.getGameBoardMap().put(bob,bobBoard);
        responseGame.getGameBoardMap().put(alice,aliceBoard);
        ObjectMapper mapper = new ObjectMapper();
        try {
            playResponse = mapper.writeValueAsString(responseGame);
        } catch (JsonProcessingException e) {
            log.error("error while converting Stores to JSON", e);
        }
        return playResponse;
    }
}