package com.bol.mancala.usecase.rules;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


public class FinalizeGameRule implements GameRule{

    private static final Logger log = LoggerFactory.getLogger(FinalizeGameRule.class);

    @Override
    public void apply(Game game, GameBoard currentPlayerGameBoard, Integer finalIndex) {
        log.info("execute rule for Player : {} in Game : {}", game.getCurrentPlayerName(), game.getGameId());
        boolean isGameOver = game.getGameBoardList().stream()
                .mapToInt(gameBoard -> Arrays
                        .stream(gameBoard.getBoard())
                        .sum())
                .anyMatch(i -> i == 0);
        if (isGameOver) {
            game.getGameBoardList().forEach(gameBoard -> {
                        gameBoard.addToMancala(Arrays.stream(gameBoard.getBoard()).sum());
                        gameBoard.setBoard(new int[]{0, 0, 0, 0, 0, 0});
                    });
            game.setGameStatus(Game.GameStatus.GAME_OVER);
        }
    }

    @Override
    public Integer getPriority() {
        return 2;
    }
}
