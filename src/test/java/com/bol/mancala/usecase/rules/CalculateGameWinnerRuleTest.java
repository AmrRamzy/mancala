package com.bol.mancala.usecase.rules;

import com.bol.mancala.entity.dto.GameRuleDto;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculateGameWinnerRuleTest {

    private GameBoard bobGameBoard;
    private Game game;
    private CalculateGameWinnerRule calculateGameWinnerRule;
    private GameRuleDto gameRuleDto;

    @BeforeEach
    void setUp() {

        calculateGameWinnerRule = new CalculateGameWinnerRule();
        bobGameBoard = new GameBoard("Bob");
        GameBoard aliceGameBoard = new GameBoard("Alice");
        game = new Game();
        game.getGameBoardList().add(bobGameBoard);
        game.getGameBoardList().add(aliceGameBoard);
        game.setGameStatus(Game.GameStatus.GAME_OVER);
        bobGameBoard.setMancala(10);
        gameRuleDto = new GameRuleDto(game, null,null);
    }

    @Test
    void apply() {
        calculateGameWinnerRule.apply(gameRuleDto);
        assertEquals(bobGameBoard.getPlayerName(), game.getGameWinnerPlayerName());
    }

    @Test
    void apply_gameInProgress() {
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        calculateGameWinnerRule.apply(gameRuleDto);
        assertNull(game.getGameWinnerPlayerName());
    }

    @Test
    void apply_null() {
        calculateGameWinnerRule.apply(new GameRuleDto());
        assertNull(game.getGameWinnerPlayerName());
    }

    @Test
    void getPriority() {
        assertAll(() -> calculateGameWinnerRule.getPriority());

    }
}