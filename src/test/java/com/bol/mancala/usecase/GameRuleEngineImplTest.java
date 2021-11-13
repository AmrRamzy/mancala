package com.bol.mancala.usecase;

import com.bol.mancala.entity.dto.GameRuleDto;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.rules.FinalizeGameRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameRuleEngineImplTest {

    private GameBoard bobGameBoard;
    private GameBoard aliceGameBoard;
    private Game game;
    private GameRuleEngineImpl gameRuleEngine;
    private GameRuleDto gameRuleDto;

    @BeforeEach
    void setUp() {

        gameRuleEngine = new GameRuleEngineImpl();
        bobGameBoard = new GameBoard("Bob");
        aliceGameBoard = new GameBoard("Alice");
        game = new Game();
        game.getGameBoardList().add(bobGameBoard);
        game.getGameBoardList().add(aliceGameBoard);
        game.setGameStatus(Game.GameStatus.GAME_OVER);
        bobGameBoard.setBoard(new int[]{0, 0, 0, 0, 0, 0});
        bobGameBoard.setMancala(10);
        aliceGameBoard.setBoard(new int[]{0, 6, 2, 1, 1, 0});
        aliceGameBoard.setMancala(1);
        gameRuleDto = new GameRuleDto(game, bobGameBoard,3);
    }

    @Test
    void applyGameRules() {
        gameRuleEngine.applyGameRules(gameRuleDto);
        assertEquals(Game.GameStatus.GAME_OVER,game.getGameStatus());
        assertEquals(11, aliceGameBoard.getMancala());
        assertEquals(aliceGameBoard.getPlayerName(), game.getGameWinnerPlayerName());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0},aliceGameBoard.getBoard());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0},bobGameBoard.getBoard());
    }
}