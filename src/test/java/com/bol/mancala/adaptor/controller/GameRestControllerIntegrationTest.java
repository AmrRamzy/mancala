package com.bol.mancala.adaptor.controller;

import com.bol.mancala.adaptor.repository.GameRespository;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.*;
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
@Import({GamePlayImpl.class, GameManagementImpl.class,GameRuleEngineImpl.class})
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
    private GameRuleEngine gameRuleEngine;
    @Autowired
    private GamePlay gamePlay;

    private GameBoard bobBoard;
    private GameBoard aliceBoard;

    @BeforeEach
    void setUp() {

//        gamePlay = new GamePlayImpl(gameManagement);
        ArrayList<Game> gameList = new ArrayList<>();
        game = new Game();
        game.setGameId(STORE_ID);
        game.setGameStatus(Game.GameStatus.PLAYABLE);
        bobBoard = new GameBoard(bob);
        game.getGameBoardList().add(bobBoard);
        aliceBoard = new GameBoard(alice);
        game.getGameBoardList().add(aliceBoard);
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.currentPlayerName").value(alice));
    }

    @Test
    void play() throws Exception {

        //set response
        GameBoard bobBoardResponse = new GameBoard(bob);
        bobBoardResponse.setBoard(new int[]{7,7,6,6,6,6});
        GameBoard aliceBoardResponse = new GameBoard(alice);
        aliceBoardResponse.setBoard(new int[]{6,6,0,7,7,7});
        aliceBoardResponse.setMancala(1);
        Game responseGame = new Game();
        responseGame.setGameId(game.getGameId());
        responseGame.setGameStatus(Game.GameStatus.IN_PROGRESS);
        responseGame.setLastMoveStatus(Game.LastMoveStatus.OTHER_BOARD);
        responseGame.setCurrentPlayerName(bob);
        responseGame.getGameBoardList().add(bobBoardResponse);
        responseGame.getGameBoardList().add(aliceBoardResponse);
        String playResponse = createResponseJson(responseGame);

        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        game.setCurrentPlayerName(alice);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("playerName", alice);
        params.add("index", "3");
        mockMvc.perform(patch(STORE_BASE_URL + "/" + STORE_ID + "/play").queryParams(params)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(playResponse));

    }

    @Test
    void play_collectOpponentStones() throws Exception {

        //set response
        GameBoard bobBoardResponse = new GameBoard(bob);
        bobBoardResponse.setBoard(new int[]{6, 6, 6, 0, 0, 6});
        bobBoardResponse.setMancala(8);
        GameBoard aliceBoardResponse = new GameBoard(alice);
        aliceBoardResponse.setBoard(new int[]{6, 0, 6, 6, 6, 6});
        Game responseGame = new Game();
        responseGame.setGameId(game.getGameId());
        responseGame.setGameStatus(Game.GameStatus.IN_PROGRESS);
        responseGame.setLastMoveStatus(Game.LastMoveStatus.CURRENT_BOARD);
        responseGame.setCurrentPlayerName(alice);
        responseGame.getGameBoardList().add(bobBoardResponse);
        responseGame.getGameBoardList().add(aliceBoardResponse);
        String playResponse = createResponseJson(responseGame);

        bobBoard.setBoard(new int[]{6, 6, 6, 1, 0, 6});
        bobBoard.setMancala(0);
        aliceBoard.setBoard(new int[]{6, 7, 6, 6, 6, 6});
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        game.setCurrentPlayerName(bob);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("playerName", bob);
        params.add("index", "4");

        mockMvc.perform(patch(STORE_BASE_URL + "/" + STORE_ID + "/play").queryParams(params)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(playResponse));

    }

    @Test
    void play_collectOpponentStones_lastPit() throws Exception {

        //set response
        GameBoard bobBoardResponse = new GameBoard(bob);
        bobBoardResponse.setBoard(new int[]{6, 6, 6, 3, 0, 0});
        bobBoardResponse.setMancala(8);
        GameBoard aliceBoardResponse = new GameBoard(alice);
        aliceBoardResponse.setBoard(new int[]{0, 6, 6, 6, 6, 6});
        Game responseGame = new Game();
        responseGame.setGameId(game.getGameId());
        responseGame.setGameStatus(Game.GameStatus.IN_PROGRESS);
        responseGame.setLastMoveStatus(Game.LastMoveStatus.CURRENT_BOARD);
        responseGame.setCurrentPlayerName(alice);
        responseGame.getGameBoardList().add(bobBoardResponse);
        responseGame.getGameBoardList().add(aliceBoardResponse);
        String playResponse = createResponseJson(responseGame);

        bobBoard.setBoard(new int[]{6, 6, 6, 3, 1, 0});
        bobBoard.setMancala(0);
        aliceBoard.setBoard(new int[]{7, 6, 6, 6, 6, 6});
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        game.setCurrentPlayerName(bob);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("playerName", bob);
        params.add("index", "5");

        mockMvc.perform(patch(STORE_BASE_URL + "/" + STORE_ID + "/play").queryParams(params)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(playResponse));

    }

    @Test
    void play_lastStoneInMancala() throws Exception {

        //set response
        GameBoard bobBoardResponse = new GameBoard(bob);
        bobBoardResponse.setBoard(new int[]{6, 6, 6, 1, 0, 7});
        bobBoardResponse.setMancala(1);
        GameBoard aliceBoardResponse = new GameBoard(alice);
        aliceBoardResponse.setBoard(new int[]{6, 7, 6, 6, 6, 6});
        Game responseGame = new Game();
        responseGame.setGameId(game.getGameId());
        responseGame.setGameStatus(Game.GameStatus.IN_PROGRESS);
        responseGame.setLastMoveStatus(Game.LastMoveStatus.MANCALA);
        responseGame.setCurrentPlayerName(bob);
        responseGame.getGameBoardList().add(bobBoardResponse);
        responseGame.getGameBoardList().add(aliceBoardResponse);
        String playResponse = createResponseJson(responseGame);

        bobBoard.setBoard(new int[]{6, 6, 6, 1, 2, 6});
        bobBoard.setMancala(0);
        aliceBoard.setBoard(new int[]{6, 7, 6, 6, 6, 6});
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        game.setCurrentPlayerName(bob);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("playerName", bob);
        params.add("index", "5");

        mockMvc.perform(patch(STORE_BASE_URL + "/" + STORE_ID + "/play").queryParams(params)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(playResponse));

    }

    @Test
    void play_gameOver() throws Exception {

        //set response
        GameBoard bobBoardResponse = new GameBoard(bob);
        bobBoardResponse.setBoard(new int[]{0, 0, 0, 0, 0, 0});
        bobBoardResponse.setMancala(11);
        GameBoard aliceBoardResponse = new GameBoard(alice);
        aliceBoardResponse.setBoard(new int[]{0, 0, 0, 0, 0, 0});
        aliceBoardResponse.setMancala(10);
        Game responseGame = new Game();
        responseGame.setGameId(game.getGameId());
        responseGame.setGameStatus(Game.GameStatus.GAME_OVER);
        responseGame.setCurrentPlayerName(alice);
        responseGame.setGameWinnerPlayerName(bob);
        responseGame.getGameBoardList().add(bobBoardResponse);
        responseGame.getGameBoardList().add(aliceBoardResponse);
        responseGame.setLastMoveStatus(Game.LastMoveStatus.CURRENT_BOARD);
        String playResponse = createResponseJson(responseGame);

        bobBoard.setBoard(new int[]{0, 0, 0, 1, 0, 0});
        bobBoard.setMancala(10);
        aliceBoard.setBoard(new int[]{0, 0, 0, 6, 4, 0});
        aliceBoard.setMancala(0);
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        game.setCurrentPlayerName(bob);
        game.setGameWinnerPlayerName(bob);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("playerName", bob);
        params.add("index", "4");

        mockMvc.perform(patch(STORE_BASE_URL + "/" + STORE_ID + "/play").queryParams(params)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(playResponse));

    }

    private String createResponseJson(Game responseGame) {
        String playResponse = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            playResponse = mapper.writeValueAsString(responseGame);
        } catch (JsonProcessingException e) {
            log.error("error while converting Stores to JSON", e);
        }
        return playResponse;
    }
}