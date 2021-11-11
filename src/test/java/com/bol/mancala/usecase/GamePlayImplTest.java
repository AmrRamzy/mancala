package com.bol.mancala.usecase;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.exception.GameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class GamePlayImplTest {

    private GamePlay gamePlay;
    private GameBoard bobGameBoard;
    private GameBoard aliceGameBoard;
    private Game game;

    @BeforeEach
    void setUp() {

        game = new Game();
        game.getGameBoardMap().put("Bob", new GameBoard("Bob"));
        game.getGameBoardMap().put("Alice",new GameBoard("Alice"));
        bobGameBoard = game.getGameBoardMap().get("Bob");
        aliceGameBoard = game.getGameBoardMap().get("Alice");

        GameManagement gameManagement = Mockito.mock(GameManagement.class);
        Mockito.when(gameManagement.loadGame("1")).thenReturn(game);
        //return same object sent to method
        Mockito.when(gameManagement.saveGame(any(Game.class))).thenAnswer((invocation) ->invocation.getArguments()[0]);

        gamePlay = new GamePlayImpl(gameManagement);
    }
    @Test
    void startGame() {
        game.setGamePlayable(true);
        gamePlay.startGame("1","Bob");
        assertTrue(game.isGameInProgress());
    }

    @Test
    void play_playerNotFound() {
        game.setGameInProgress(true);
        assertThrows(GameException.class,()->gamePlay.play("1","wrong Player",3));
    }

    @Test
    void play_gameNotStarted() {
        assertThrows(GameException.class,()->gamePlay.play("1","Bob",3));
    }

    @Test
    void play() {
        game.setGameInProgress(true);
        game.setCurrentPlayerName(bobGameBoard.getPlayerName());
        gamePlay.play("1","Bob",3);
        assertArrayEquals(new int[]{6, 6, 0, 7, 7, 7}, bobGameBoard.getBoard());
        assertArrayEquals(new int[]{7, 7, 6, 6, 6, 6}, aliceGameBoard.getBoard());
        assertEquals(1, bobGameBoard.getMancala());
        assertEquals(0, aliceGameBoard.getMancala());
        assertEquals(aliceGameBoard.getPlayerName(), game.getCurrentPlayerName());
        assertTrue(game.isGameInProgress());
    }

    @Test
    void play_lastStoneInEmptyPit() {
        game.setGameInProgress(true);
        game.setCurrentPlayerName(bobGameBoard.getPlayerName());
        bobGameBoard.setBoard(new int []{6, 2, 1, 0, 3, 7});
        gamePlay.play("1","Bob",2);
        assertArrayEquals(new int[]{6, 0, 2, 0, 3, 7}, bobGameBoard.getBoard());
        assertArrayEquals(new int[]{6, 6, 0, 6, 6, 6}, aliceGameBoard.getBoard());
        assertEquals(7, bobGameBoard.getMancala());
        assertEquals(0, aliceGameBoard.getMancala());
        assertEquals(aliceGameBoard.getPlayerName(), game.getCurrentPlayerName());
        assertTrue(game.isGameInProgress());
    }

    @Test
    void play_lastStoneInMancala() {
        game.setGameInProgress(true);
        game.setCurrentPlayerName(aliceGameBoard.getPlayerName());
        aliceGameBoard.setBoard(new int []{6, 2, 1, 3, 3, 7});
        gamePlay.play("1","Alice",4);
        assertArrayEquals(new int[]{6, 6, 6, 6, 6, 6}, bobGameBoard.getBoard());
        assertArrayEquals(new int[]{6, 2, 1, 0, 4, 8}, aliceGameBoard.getBoard());
        assertEquals(0, bobGameBoard.getMancala());
        assertEquals(1, aliceGameBoard.getMancala());
        assertEquals(aliceGameBoard.getPlayerName(), game.getCurrentPlayerName());
        assertTrue(game.isGameInProgress());
    }

    @Test
    void play_notPlayerTurn() {
        game.setGameInProgress(true);
        game.setCurrentPlayerName(aliceGameBoard.getPlayerName());
        gamePlay.play("1","Bob",4);
        assertArrayEquals(new int[]{6, 6, 6, 6, 6, 6}, bobGameBoard.getBoard());
        assertArrayEquals(new int[]{6, 6, 6, 6, 6, 6}, aliceGameBoard.getBoard());
        assertEquals(0, bobGameBoard.getMancala());
        assertEquals(0, aliceGameBoard.getMancala());
        assertEquals(aliceGameBoard.getPlayerName(), game.getCurrentPlayerName());
        assertTrue(game.isGameInProgress());
    }

    @Test
    void play_gameOver() {
        game.setGameInProgress(true);
        game.setCurrentPlayerName(bobGameBoard.getPlayerName());
        bobGameBoard.setBoard(new int []{0, 0, 0, 0, 0, 3});
        bobGameBoard.setMancala(40);
        aliceGameBoard.setBoard(new int []{0, 0, 1, 1, 1, 1});
        aliceGameBoard.setMancala(25);
        gamePlay.play("1","Bob",6);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, bobGameBoard.getBoard());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, aliceGameBoard.getBoard());
        assertEquals(41, bobGameBoard.getMancala());
        assertEquals(31, aliceGameBoard.getMancala());
        assertEquals(bobGameBoard.getPlayerName(), game.getGameWinnerPlayerName());
        assertFalse(game.isGameInProgress());
    }
}