package com.bol.mancala.usecase.rules;

import com.bol.mancala.entity.dto.GameRuleDto;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.util.GameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LastStoneInSameCurrentPlayerBoardRule implements GameRule {

    private static final Logger log = LoggerFactory.getLogger(LastStoneInSameCurrentPlayerBoardRule.class);

    @Override
    public void apply(GameRuleDto gameRuleDto) {
        log.info("execute rule for gameRuleDto : {}", gameRuleDto);

        if (gameRuleDto == null || gameRuleDto.getGame() == null || gameRuleDto.getCurrentPlayerGameBoard() == null || gameRuleDto.getFinalIndex() == null)
            return;

        Game game = gameRuleDto.getGame();
        GameBoard currentPlayerGameBoard = gameRuleDto.getCurrentPlayerGameBoard();
        Integer finalIndex = gameRuleDto.getFinalIndex();
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
