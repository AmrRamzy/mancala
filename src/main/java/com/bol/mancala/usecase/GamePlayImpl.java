package com.bol.mancala.usecase;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.exception.GameException;
import com.bol.mancala.usecase.util.GameUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@RequiredArgsConstructor
@Service
public class GamePlayImpl implements GamePlay {

    private static final Logger log = LoggerFactory.getLogger(GamePlayImpl.class);
    private final GameManagement gameManagement;
    private final GameRuleEngine gameRuleEngine;

    @Override
    public synchronized Game play(String gameId, String playerName, int currentIndex) {
        log.info("run a play for Player : {} in Game : {} with index : {}", playerName, gameId, currentIndex);
        Game game = gameManagement.loadGame(gameId);
        Predicate<GameBoard> filterPlayerByNamePredicate = p -> playerName.equals(p.getPlayerName());
        if (game.getGameStatus().equals(Game.GameStatus.IN_PROGRESS)) {
            GameBoard currentPlayerGameBoard = game.getGameBoardList().stream()
                    .filter(filterPlayerByNamePredicate)
                    .findFirst()
                    .orElseThrow(() -> new GameException("Player did not join game"));

            if (game.getCurrentPlayerName().equals(currentPlayerGameBoard.getPlayerName())) {
                int lastStoneMovedIndex = moveStones(game, currentPlayerGameBoard, currentIndex);
                GameBoard nextPlayerBoard = GameUtil.getNextPlayerBoard(game, currentPlayerGameBoard);
                game.setCurrentPlayerName(nextPlayerBoard.getPlayerName());
                gameRuleEngine.applyGameRules(game, currentPlayerGameBoard, lastStoneMovedIndex);
            }
        } else {
            throw new GameException("game was not started.");
        }
        return gameManagement.saveGame(game);
    }

    private int moveStones(Game game, GameBoard currentPlayerGameBoard, int index) {
        int lastStoneMovedIndex = index;
        int stonesToMoveIndex = index - 1;
        int[] currentPlayerBoard = currentPlayerGameBoard.getBoard();
        GameBoard nextGameBoard = currentPlayerGameBoard;
        while (currentPlayerBoard[stonesToMoveIndex] > 0) {
            lastStoneMovedIndex = moveStonesInBoard(nextGameBoard, currentPlayerGameBoard, lastStoneMovedIndex, stonesToMoveIndex);
            game.setLastMoveStatus(Game.LastMoveStatus.OTHER_BOARD);
            if (currentPlayerGameBoard.equals(nextGameBoard)) {
                game.setLastMoveStatus(Game.LastMoveStatus.CURRENT_BOARD);
                if (currentPlayerBoard[stonesToMoveIndex] > 0) {
                    nextGameBoard.setMancala(nextGameBoard.getMancala() + 1);
                    currentPlayerBoard[stonesToMoveIndex] = currentPlayerBoard[stonesToMoveIndex] - 1;
                    game.setLastMoveStatus(Game.LastMoveStatus.MANCALA);
                }
            }

            nextGameBoard = GameUtil.getNextPlayerBoard(game, currentPlayerGameBoard);
        }
        return lastStoneMovedIndex;
    }

    private int moveStonesInBoard(GameBoard boardToMoveStonesIn, GameBoard currentPlayerBoard, int index, int stonesToMoveIndex) {
        int lastStoneMovedIndex = index;
        while (lastStoneMovedIndex < boardToMoveStonesIn.getBoard().length && currentPlayerBoard.getBoard()[stonesToMoveIndex] > 0) {
            boardToMoveStonesIn.getBoard()[lastStoneMovedIndex] = boardToMoveStonesIn.getBoard()[lastStoneMovedIndex] + 1;
            currentPlayerBoard.getBoard()[stonesToMoveIndex] = currentPlayerBoard.getBoard()[stonesToMoveIndex] - 1;
            lastStoneMovedIndex++;
        }
        if (lastStoneMovedIndex == boardToMoveStonesIn.getBoard().length)
            lastStoneMovedIndex = 0;
        else
            lastStoneMovedIndex--;

        return lastStoneMovedIndex;
    }


}
