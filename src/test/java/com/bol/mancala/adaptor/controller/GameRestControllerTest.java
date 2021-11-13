package com.bol.mancala.adaptor.controller;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.GameManagement;
import com.bol.mancala.usecase.GamePlay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

class GameRestControllerTest {

    private GameRestController gameRestController;

    @BeforeEach
    void setUp() {
        GamePlay gamePlay = Mockito.mock(GamePlay.class);
        GameManagement gameManagement = Mockito.mock(GameManagement.class);
        gameRestController = new GameRestController(gamePlay, gameManagement);

        ArrayList<Game> gameList = new ArrayList<>();
        Game game = new Game();
        game.setGameId("1234");
        game.getGameBoardList().add(new GameBoard("Bob"));
        game.getGameBoardList().add(new GameBoard("Alice"));
        gameList.add(game);
        Mockito.when(gameManagement.loadGame(anyString())).thenReturn(game);
        Mockito.when(gameManagement.getAllGames()).thenReturn(gameList);
        Mockito.when(gameManagement.createAndJoinGame(anyString(), anyString())).thenReturn(game);

        Mockito.when(gamePlay.play(anyString(), anyString(), anyInt())).thenReturn(game);

    }

    @Test
    void getAllGames() {
        List<Game> gameList = gameRestController.getAllGames();
        assertFalse(gameList.isEmpty());
    }

    @Test
    void getGame() {
        Game game = gameRestController.getGame("1234");
        assertNotNull(game);
    }

    @Test
    void createAndJoinGame() {
        Game game = gameRestController.createAndJoinGame("Bob", "Alice");
        assertNotNull(game);
    }

    @Test
    void play() {
        Game game = gameRestController.play("1234", "Alice", 3);
        assertNotNull(game);
    }
}