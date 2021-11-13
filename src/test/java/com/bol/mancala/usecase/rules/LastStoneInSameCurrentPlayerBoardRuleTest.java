package com.bol.mancala.usecase.rules;

import com.bol.mancala.entity.dto.GameRuleDto;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LastStoneInSameCurrentPlayerBoardRuleTest {
    private GameBoard bobGameBoard;
    private GameBoard aliceGameBoard;
    private Game game;
    private LastStoneInSameCurrentPlayerBoardRule lastStoneInSameCurrentPlayerBoardRule;
    private GameRuleDto gameRuleDto;

    @BeforeEach
    void setUp() {

        lastStoneInSameCurrentPlayerBoardRule = new LastStoneInSameCurrentPlayerBoardRule();
        bobGameBoard = new GameBoard("Bob");
        aliceGameBoard = new GameBoard("Alice");
        game = new Game();
        game.getGameBoardList().add(bobGameBoard);
        game.getGameBoardList().add(aliceGameBoard);
        game.setLastMoveStatus(Game.LastMoveStatus.CURRENT_BOARD);
        bobGameBoard.setBoard(new int[]{0, 5, 2, 0, 1, 0});
        bobGameBoard.setMancala(10);
        aliceGameBoard.setBoard(new int[]{0, 9, 2, 1, 7, 4});
        aliceGameBoard.setMancala(1);
        gameRuleDto = new GameRuleDto(game, bobGameBoard,4);
    }

    @Test
    void apply() {
        lastStoneInSameCurrentPlayerBoardRule.apply(gameRuleDto);
        assertEquals(20, bobGameBoard.getMancala());
        assertEquals(1, aliceGameBoard.getMancala());
        assertArrayEquals(new int[]{0, 0, 2, 1, 7, 4},aliceGameBoard.getBoard());
        assertArrayEquals(new int[]{0, 5, 2, 0, 0, 0},bobGameBoard.getBoard());
    }

    @Test
    void apply_null() {
        lastStoneInSameCurrentPlayerBoardRule.apply(new GameRuleDto());
        assertEquals(10, bobGameBoard.getMancala());
        assertEquals(1, aliceGameBoard.getMancala());
        assertArrayEquals(new int[]{0, 9, 2, 1, 7, 4},aliceGameBoard.getBoard());
        assertArrayEquals(new int[]{0, 5, 2, 0, 1, 0},bobGameBoard.getBoard());
    }

    @Test
    void apply_invalidRule() {
        game.setLastMoveStatus(Game.LastMoveStatus.CURRENT_BOARD);
        bobGameBoard.setBoard(new int[]{0, 0, 1, 0, 0, 0});
        lastStoneInSameCurrentPlayerBoardRule.apply(gameRuleDto);
        assertEquals(10, bobGameBoard.getMancala());
        assertEquals(1, aliceGameBoard.getMancala());
        assertArrayEquals(new int[]{0, 9, 2, 1, 7, 4},aliceGameBoard.getBoard());
        assertArrayEquals(new int[]{0, 0, 1, 0, 0, 0},bobGameBoard.getBoard());
    }

    @Test
    void apply_notSameBoard() {
        game.setLastMoveStatus(Game.LastMoveStatus.OTHER_BOARD);
        lastStoneInSameCurrentPlayerBoardRule.apply(gameRuleDto);
        assertEquals(10, bobGameBoard.getMancala());
        assertEquals(1, aliceGameBoard.getMancala());
        assertArrayEquals(new int[]{0, 9, 2, 1, 7, 4},aliceGameBoard.getBoard());
        assertArrayEquals(new int[]{0, 5, 2, 0, 1, 0},bobGameBoard.getBoard());
    }

    @Test
    void getPriority() {
        assertAll(() -> lastStoneInSameCurrentPlayerBoardRule.getPriority());

    }
}