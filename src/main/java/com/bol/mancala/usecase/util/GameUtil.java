package com.bol.mancala.usecase.util;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;

public class GameUtil {

    private GameUtil() {
    }

    public static GameBoard getNextPlayerBoard(Game game, GameBoard currentPlayerBoard) {
        int currentPlayerIndex = game.getGameBoardList().indexOf(currentPlayerBoard);
        int nextPlayerIndex = currentPlayerIndex + 1;
        if (nextPlayerIndex >= game.getGameBoardList().size())
            nextPlayerIndex = 0;
        return game.getGameBoardList().get(nextPlayerIndex);
    }
}
