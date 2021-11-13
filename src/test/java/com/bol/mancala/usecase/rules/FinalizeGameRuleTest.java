package com.bol.mancala.usecase.rules;

import com.bol.mancala.entity.dto.GameRuleDto;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinalizeGameRuleTest {

    private GameBoard bobGameBoard;
    private GameBoard aliceGameBoard;
    private Game game;
    private FinalizeGameRule finalizeGameRule;
    private GameRuleDto gameRuleDto;

    @BeforeEach
    void setUp() {

        finalizeGameRule = new FinalizeGameRule();
        bobGameBoard = new GameBoard("Bob");
        aliceGameBoard = new GameBoard("Alice");
        game = new Game();
        game.getGameBoardList().add(bobGameBoard);
        game.getGameBoardList().add(aliceGameBoard);
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        bobGameBoard.setBoard(new int[]{0, 0, 0, 0, 0, 0});
        bobGameBoard.setMancala(10);
        aliceGameBoard.setBoard(new int[]{0, 6, 2, 1, 1, 0});
        aliceGameBoard.setMancala(1);
        gameRuleDto = new GameRuleDto(game, null,null);
    }

    @Test
    void apply() {
        finalizeGameRule.apply(gameRuleDto);
        assertEquals(Game.GameStatus.GAME_OVER,game.getGameStatus());
        assertEquals(11, aliceGameBoard.getMancala());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0},aliceGameBoard.getBoard());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0},bobGameBoard.getBoard());
    }

    @Test
    void apply_gameNotOver() {
        bobGameBoard.setBoard(new int[]{0, 0, 1, 0, 0, 0});
        finalizeGameRule.apply(gameRuleDto);
        assertEquals(Game.GameStatus.IN_PROGRESS,game.getGameStatus());
        assertEquals(1, aliceGameBoard.getMancala());
        assertArrayEquals(new int[]{0, 0, 1, 0, 0, 0},bobGameBoard.getBoard());
    }

    @Test
    void apply_null() {
        finalizeGameRule.apply(new GameRuleDto());
        assertEquals(Game.GameStatus.IN_PROGRESS,game.getGameStatus());
        assertEquals(1, aliceGameBoard.getMancala());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0},bobGameBoard.getBoard());
    }

    @Test
    void getPriority() {
        assertAll(() -> finalizeGameRule.getPriority());

    }
}