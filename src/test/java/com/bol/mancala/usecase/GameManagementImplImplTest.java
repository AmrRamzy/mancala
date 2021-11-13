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
    private GameBoard bobGameBoard;

    @BeforeEach
    void setUp() {
        gameRespository = Mockito.mock(GameRespository.class);
        ArrayList<Game> gameList = new ArrayList<>();
        Game game = new Game();
        bobGameBoard = new GameBoard("Bob");
        game.getGameBoardList().add(bobGameBoard);
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
        assertTrue(gameList.get(0).getGameBoardList().stream().anyMatch(gb -> gb.getPlayerName().equals(bobGameBoard.getPlayerName())));
    }

    @Test
    void loadGame() {
        Game game = gameManagement.loadGame("1");
        assertNotNull(game.getGameId());
    }

    @Test
    void loadGame_gameNotFound() {
        assertThrows(GameException.class,()->gameManagement.loadGame("wrong ID"), "Game not found");
    }

    @Test
    void createAndJoinGame() {
        Mockito.when(gameRespository.findById("1")).thenReturn(Optional.of(new Game()));
        String bob = "Bob";
        String alice = "Alice";
        Game game = gameManagement.createAndJoinGame(bob, alice);
        assertEquals(2, game.getGameBoardList().size());
        assertTrue(game.getGameBoardList().stream().anyMatch(gb -> gb.getPlayerName().equals(bob)));
        assertTrue(game.getGameBoardList().stream().anyMatch(gb -> gb.getPlayerName().equals(alice)));
        assertEquals(Game.GameStatus.IN_PROGRESS, game.getGameStatus());
    }

    @Test
    void saveGame() {
        Game newGame= new Game();
        newGame.getGameBoardList().add(new GameBoard("Alice"));
        Game game = gameManagement.saveGame(newGame);
        assertEquals(newGame,game);
    }

}