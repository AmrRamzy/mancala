package com.bol.mancala.usecase;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.exception.GameException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Service
public class GamePlayImpl implements GamePlay {

    private static final Logger log = LoggerFactory.getLogger(GamePlayImpl.class);
    private final GameManagement gameManagement;

    @Override
    public synchronized Game play(String gameId, String playerName, int currentIndex) {
        log.info("run a play for Player : {} in Game : {} with index : {}", playerName, gameId, currentIndex);
        Game game = gameManagement.loadGame(gameId);
        Predicate<GameBoard> filterPlayerByNamePredicate = p -> playerName.equals(p.getPlayerName());
        if (game.isGameInProgress()) {
            GameBoard currentPlayerGameBoard = game.getGameBoardMap().values().stream()
                    .filter(filterPlayerByNamePredicate)
                    .findFirst()
                    .orElseThrow(() -> new GameException("Player did not join game"));
            GameBoard otherPlayerGameBoard = game.getGameBoardMap().values().stream()
                    .filter(filterPlayerByNamePredicate.negate())
                    .findFirst()
                    .orElseThrow(() -> new GameException("Player did not join game"));


            if (checkIfCurrentPlayerTurn(game, currentPlayerGameBoard)) {
                int stonesCount = currentPlayerGameBoard.getBoard()[currentIndex - 1];
                if (stonesCount > 0) {
                    currentPlayerGameBoard.getBoard()[currentIndex - 1] = 0;
                    game.setCurrentPlayerName(otherPlayerGameBoard.getPlayerName());
                    moveStones(game, currentPlayerGameBoard, otherPlayerGameBoard, currentIndex, stonesCount);

                }
            }
            if (finalizeGame(game, currentPlayerGameBoard, otherPlayerGameBoard))
                calculateWinner(game, currentPlayerGameBoard, otherPlayerGameBoard);
        } else {
            throw new GameException("game was not started.");
        }
        return gameManagement.saveGame(game);
    }

    private void moveStones(Game game, GameBoard currentPlayerGameBoard, GameBoard otherPlayerGameBoard, int index, int count) {
        log.debug("move {} stones for play : {} in Game : {} from index : {}", count, currentPlayerGameBoard.getPlayerName(), game.getGameId(), index);
        int stonesCount = moveStonesInSamePlayerBoard(currentPlayerGameBoard, otherPlayerGameBoard, index, count);
        if (stonesCount == 1) {
            currentPlayerGameBoard.setMancala(currentPlayerGameBoard.getMancala() + 1);
            game.setCurrentPlayerName(currentPlayerGameBoard.getPlayerName());
        } else if (stonesCount > 1) {
            currentPlayerGameBoard.setMancala(currentPlayerGameBoard.getMancala() + 1);
            stonesCount = moveStonesInOtherPlayerBoard(otherPlayerGameBoard, --stonesCount);
            if (stonesCount > 0) {
                moveStones(game, currentPlayerGameBoard, otherPlayerGameBoard, 0, stonesCount);
            }
        }

    }

    private boolean finalizeGame(Game game, GameBoard currentPlayerGameBoard, GameBoard otherPlayerGameBoard) {
        log.debug("check if Game : {} is over", game.getGameId());
        boolean isGameOver = false;
        if (currentPlayerGameBoard != null && otherPlayerGameBoard != null) {
            int currentPlayerBoardSum = Arrays.stream(currentPlayerGameBoard.getBoard()).sum();
            int otherPlayerBoardSum = Arrays.stream(otherPlayerGameBoard.getBoard()).sum();
            if (currentPlayerBoardSum == 0 || otherPlayerBoardSum == 0) {
                currentPlayerGameBoard.addToMancala(currentPlayerBoardSum);
                otherPlayerGameBoard.addToMancala(otherPlayerBoardSum);
                currentPlayerGameBoard.setBoard(new int[]{0, 0, 0, 0, 0, 0});
                otherPlayerGameBoard.setBoard(new int[]{0, 0, 0, 0, 0, 0});
                isGameOver = true;
                game.setGameInProgress(false);
            }
        }

        return isGameOver;
    }

    private void calculateWinner(Game game, GameBoard currentPlayerGameBoard, GameBoard otherPlayerGameBoard) {
        log.debug("calculate Game : {} winner", game.getGameId());
        if (currentPlayerGameBoard.getMancala() > otherPlayerGameBoard.getMancala())
            game.setGameWinnerPlayerName(currentPlayerGameBoard.getPlayerName());
        else if (otherPlayerGameBoard.getMancala() > currentPlayerGameBoard.getMancala())
            game.setGameWinnerPlayerName(otherPlayerGameBoard.getPlayerName());
    }

    private boolean checkIfCurrentPlayerTurn(Game game, GameBoard gameBoard) {
        log.debug("check if it is player : {} turn in game {}", gameBoard.getPlayerName(), game.getGameId());
        return  game.getCurrentPlayerName().equals(gameBoard.getPlayerName());
    }

    private int moveStonesInSamePlayerBoard(GameBoard currentPlayerGameBoard, GameBoard otherPlayerGameBoard, int index, int count) {
        log.debug("move {} stones in play : {} board from index : {}", count, currentPlayerGameBoard.getPlayerName(), index);
        int stonesCount = count;
        int i = index;
        while (i < currentPlayerGameBoard.getBoard().length && stonesCount > 0) {
            currentPlayerGameBoard.getBoard()[i] = currentPlayerGameBoard.getBoard()[i] + 1;
            stonesCount--;
            i++;
        }
        if (stonesCount == 0 && currentPlayerGameBoard.getBoard()[i - 1] == 1) {
            int otherPlayerStones = otherPlayerGameBoard.getBoard()[otherPlayerGameBoard.getBoard().length - i];
            currentPlayerGameBoard.setMancala(currentPlayerGameBoard.getMancala() + otherPlayerStones + 1);
            currentPlayerGameBoard.getBoard()[i - 1] = 0;
            otherPlayerGameBoard.getBoard()[otherPlayerGameBoard.getBoard().length - i] = 0;
        }
        return stonesCount;
    }

    private int moveStonesInOtherPlayerBoard(GameBoard gameBoard, int count) {
        log.debug("move {} stones in play : {} board", count, gameBoard.getPlayerName());
        int stonesCount = count;
        for (int i = 0; i < gameBoard.getBoard().length && stonesCount > 0; i++, stonesCount--) {
            gameBoard.getBoard()[i] = gameBoard.getBoard()[i] + 1;
        }
        return stonesCount;
    }

}
