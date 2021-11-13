package com.bol.mancala.usecase.rules;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.util.GameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LastStoneInSameCurrentPlayerBoardRule implements GameRule{

    private static final Logger log = LoggerFactory.getLogger(LastStoneInSameCurrentPlayerBoardRule.class);

    @Override
    public void apply(Game game, GameBoard currentPlayerGameBoard, Integer finalIndex) {
        log.info("execute rule for Player : {} in Game : {}", game.getCurrentPlayerName(), game.getGameId());
        if (game.getLastMoveStatus().equals(Game.LastMoveStatus.CURRENT_BOARD) && currentPlayerGameBoard.getBoard()[finalIndex] == 1) {
            GameBoard nextPlayerGameBoard = GameUtil.getNextPlayerBoard(game, currentPlayerGameBoard);
            int nextPlayerStones = nextPlayerGameBoard.getBoard()[nextPlayerGameBoard.getBoard().length - finalIndex - 1];
            currentPlayerGameBoard.addToMancala(nextPlayerStones + 1);
            currentPlayerGameBoard.getBoard()[finalIndex] = 0;
            nextPlayerGameBoard.getBoard()[nextPlayerGameBoard.getBoard().length - finalIndex - 1] = 0;
        }
    }

    @Override
    public Integer getPriority() {
        return 0;
    }
}
