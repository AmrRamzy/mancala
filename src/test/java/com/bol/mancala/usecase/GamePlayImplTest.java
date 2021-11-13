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
        game.getGameBoardList().add( new GameBoard("Bob"));
        game.getGameBoardList().add(new GameBoard("Alice"));
        bobGameBoard = game.getGameBoardList().get(0);
        aliceGameBoard = game.getGameBoardList().get(1);

        GameManagement gameManagement = Mockito.mock(GameManagement.class);
        Mockito.when(gameManagement.loadGame("1")).thenReturn(game);
        //return same object sent to method
        Mockito.when(gameManagement.saveGame(any(Game.class))).thenAnswer((invocation) ->invocation.getArguments()[0]);

        gamePlay = new GamePlayImpl(gameManagement, new GameRuleEngineImpl());
//        gamePlay = new GamePlayImpl(gameManagement);
    }

    @Test
    void play_playerNotFound() {
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        assertThrows(GameException.class,()->gamePlay.play("1","wrong Player",3));
    }

    @Test
    void play_gameNotStarted() {
        assertThrows(GameException.class,()->gamePlay.play("1","Bob",3));
    }

    @Test
    void play() {
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        game.setCurrentPlayerName(bobGameBoard.getPlayerName());
        gamePlay.play("1","Bob",3);
        assertArrayEquals(new int[]{6, 6, 0, 7, 7, 7}, bobGameBoard.getBoard());
        assertArrayEquals(new int[]{7, 7, 6, 6, 6, 6}, aliceGameBoard.getBoard());
        assertEquals(1, bobGameBoard.getMancala());
        assertEquals(0, aliceGameBoard.getMancala());
        assertEquals(aliceGameBoard.getPlayerName(), game.getCurrentPlayerName());
        assertEquals(Game.GameStatus.IN_PROGRESS,game.getGameStatus());
    }

    @Test
    void play_lastStoneInEmptyPit() {
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        game.setCurrentPlayerName(bobGameBoard.getPlayerName());
        bobGameBoard.setBoard(new int []{6, 2, 1, 0, 3, 7});
        aliceGameBoard.setBoard(new int []{4, 5, 6, 7, 8, 9});
        gamePlay.play("1","Bob",2);
        assertArrayEquals(new int[]{6, 0, 2, 0, 3, 7}, bobGameBoard.getBoard());
        assertArrayEquals(new int[]{4, 5, 0, 7, 8, 9}, aliceGameBoard.getBoard());
        assertEquals(7, bobGameBoard.getMancala());
        assertEquals(0, aliceGameBoard.getMancala());
        assertEquals(aliceGameBoard.getPlayerName(), game.getCurrentPlayerName());
        assertEquals(Game.GameStatus.IN_PROGRESS,game.getGameStatus());
    }

    @Test
    void play_lastStoneInMancala() {
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        game.setCurrentPlayerName(aliceGameBoard.getPlayerName());
        aliceGameBoard.setBoard(new int []{6, 2, 1, 3, 3, 7});
        gamePlay.play("1","Alice",4);
        assertArrayEquals(new int[]{6, 6, 6, 6, 6, 6}, bobGameBoard.getBoard());
        assertArrayEquals(new int[]{6, 2, 1, 0, 4, 8}, aliceGameBoard.getBoard());
        assertEquals(0, bobGameBoard.getMancala());
        assertEquals(1, aliceGameBoard.getMancala());
        assertEquals(aliceGameBoard.getPlayerName(), game.getCurrentPlayerName());
        assertEquals(Game.GameStatus.IN_PROGRESS,game.getGameStatus());
    }

    @Test
    void play_notPlayerTurn() {
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        game.setCurrentPlayerName(aliceGameBoard.getPlayerName());
        gamePlay.play("1","Bob",4);
        assertArrayEquals(new int[]{6, 6, 6, 6, 6, 6}, bobGameBoard.getBoard());
        assertArrayEquals(new int[]{6, 6, 6, 6, 6, 6}, aliceGameBoard.getBoard());
        assertEquals(0, bobGameBoard.getMancala());
        assertEquals(0, aliceGameBoard.getMancala());
        assertEquals(aliceGameBoard.getPlayerName(), game.getCurrentPlayerName());
        assertEquals(Game.GameStatus.IN_PROGRESS,game.getGameStatus());
    }

    @Test
    void play_gameOver() {
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
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
        assertEquals(Game.GameStatus.GAME_OVER, game.getGameStatus());
    }
}