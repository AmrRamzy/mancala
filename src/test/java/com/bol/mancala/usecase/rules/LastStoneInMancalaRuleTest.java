package com.bol.mancala.usecase.rules;

import com.bol.mancala.entity.dto.GameRuleDto;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LastStoneInMancalaRuleTest {

    private GameBoard bobGameBoard;
    private GameBoard aliceGameBoard;
    private Game game;
    private LastStoneInMancalaRule lastStoneInMancalaRule;
    private GameRuleDto gameRuleDto;

    @BeforeEach
    void setUp() {

        lastStoneInMancalaRule = new LastStoneInMancalaRule();
        bobGameBoard = new GameBoard("Bob");
        aliceGameBoard = new GameBoard("Alice");
        game = new Game();
        game.getGameBoardList().add(bobGameBoard);
        game.getGameBoardList().add(aliceGameBoard);

        game.setLastMoveStatus(Game.LastMoveStatus.MANCALA);
        game.setCurrentPlayerName(aliceGameBoard.getPlayerName());

        gameRuleDto = new GameRuleDto(game, bobGameBoard,null);
    }

    @Test
    void apply() {
        lastStoneInMancalaRule.apply(gameRuleDto);
        assertEquals(bobGameBoard.getPlayerName(), game.getCurrentPlayerName());
    }

    @Test
    void apply_invalidRule() {
        game.setLastMoveStatus(Game.LastMoveStatus.OTHER_BOARD);
        lastStoneInMancalaRule.apply(gameRuleDto);
        assertEquals(aliceGameBoard.getPlayerName(), game.getCurrentPlayerName());
    }

    @Test
    void apply_null() {
        lastStoneInMancalaRule.apply(new GameRuleDto());
        assertEquals(aliceGameBoard.getPlayerName(), game.getCurrentPlayerName());
    }

    @Test
    void getPriority() {
        assertAll(() -> lastStoneInMancalaRule.getPriority());

    }
}