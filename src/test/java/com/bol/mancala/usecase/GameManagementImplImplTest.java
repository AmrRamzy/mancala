package com.bol.mancala.usecase;

import com.bol.mancala.adaptor.repository.GameRespository;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.Player;
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
    private Player bob;
    private Player alice ;

    @BeforeEach
    void setUp() {
        bob = new Player();
        bob.setName("Bob");
        alice = new Player();
        alice.setName("Alice");
        gameRespository = Mockito.mock(GameRespository.class);
        ArrayList gameList = new ArrayList<Game>();
        gameList.add(new Game("Bob"));
        Mockito.when(gameRespository.findAll()).thenReturn(gameList);
        Mockito.when(gameRespository.findById("1")).thenReturn(Optional.of(new Game("Bob")));
        //return same object sent to method
        Mockito.when(gameRespository.insert(any(Game.class))).thenAnswer((invocation) ->invocation.getArguments()[0]);
        //return same object sent to method
        Mockito.when(gameRespository.save(any(Game.class))).thenAnswer((invocation) ->invocation.getArguments()[0]);
//        //return same object sent to method
//        Mockito.when(playerManagement.createPlayer(any(Player.class))).thenAnswer((invocation) ->invocation.getArguments()[0]);
//
//        Mockito.when(playerManagement.getPlayer("Bob")).thenReturn(bob);
//        Mockito.when(playerManagement.getPlayer("Alice")).thenReturn(alice);

        gameManagement = new GameManagementImpl(gameRespository);
    }

    @Test
    void getAllGames() {
        List<Game> gameList = gameManagement.getAllGames();
        assertTrue(gameList.get(0).getGameBoardMap().containsKey("Bob"));
    }
    @Test
    void createGame() {
        Game game = gameManagement.createGame("Bob");
        assertEquals(1, game.getGameBoardMap().size());
        assertTrue(game.getGameBoardMap().containsKey("Bob"));
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
    void joinGame() {
        Game game = gameManagement.joinGame("1", "Alice");
        assertEquals(2, game.getGameBoardMap().size());
        assertTrue(game.getGameBoardMap().containsKey("Alice"));
        assertTrue(game.isGamePlayable());
    }

    @Test
    void saveGame() {
        Game newGame= new Game("Alice");
        Game game = gameManagement.saveGame(newGame);
        assertEquals(newGame,game);
    }

}