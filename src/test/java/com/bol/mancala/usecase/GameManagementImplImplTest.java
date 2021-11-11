package com.bol.mancala.usecase;

import com.bol.mancala.adaptor.repository.GameRespository;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.exception.GameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class GameManagementImplImplTest {

    private GameRespository gameRespository;
    private GameManagement gameManagement;

    @BeforeEach
    void setUp() {
        gameRespository = Mockito.mock(GameRespository.class);
        ArrayList<Game> gameList = new ArrayList<>();
        Game game = new Game();
        game.getGameBoardMap().put("Bob", new GameBoard("Bob"));
        gameList.add(game);
        Mockito.when(gameRespository.findAll()).thenReturn(gameList);
        Mockito.when(gameRespository.findById("1")).thenReturn(Optional.of(game));
        //return same object sent to method
        Mockito.when(gameRespository.insert(any(Game.class))).thenAnswer((invocation) ->invocation.getArguments()[0]);
        //return same object sent to method
        Mockito.when(gameRespository.save(any(Game.class))).thenAnswer((invocation) ->invocation.getArguments()[0]);
        //return same object sent to method

        gameManagement = new GameManagementImpl(gameRespository);
    }

    @Test
    void getAllGames() {
        List<Game> gameList = gameManagement.getAllGames();
        assertTrue(gameList.get(0).getGameBoardMap().containsKey("Bob"));
    }

    @Test
    void loadGame() {
        Game game = gameManagement.loadGame("1");
        assertNotNull(game.getGameId());
    }

    @Test
    void loadGame_gameNotFound() {
        assertThrows(GameException.class,()->gameManagement.loadGame("wrong ID"));
    }

    @Test
    void createAndJoinGame() {
        Mockito.when(gameRespository.findById("1")).thenReturn(Optional.of(new Game()));
        Game game = gameManagement.createAndJoinGame("Bob", "Alice");
        assertEquals(2, game.getGameBoardMap().size());
        assertTrue(game.getGameBoardMap().containsKey("Bob"));
        assertTrue(game.getGameBoardMap().containsKey("Alice"));
        assertTrue(game.isGamePlayable());
    }

    @Test
    void saveGame() {
        Game newGame= new Game();
        newGame.getGameBoardMap().put("Alice", new GameBoard("Alice"));
        Game game = gameManagement.saveGame(newGame);
        assertEquals(newGame,game);
    }

}