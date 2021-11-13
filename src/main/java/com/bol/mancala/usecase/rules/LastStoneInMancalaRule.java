package com.bol.mancala.usecase.rules;

import com.bol.mancala.entity.dto.GameRuleDto;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LastStoneInMancalaRule implements GameRule {

    private static final Logger log = LoggerFactory.getLogger(LastStoneInMancalaRule.class);

    @Override
    public void apply(GameRuleDto gameRuleDto) {
        log.info("execute rule for gameRuleDto : {}", gameRuleDto);

        if (gameRuleDto == null || gameRuleDto.getGame() == null || gameRuleDto.getCurrentPlayerGameBoard() == null)
            return;

        Game game = gameRuleDto.getGame();
        GameBoard currentPlayerGameBoard = gameRuleDto.getCurrentPlayerGameBoard();
        log.info("execute rule for Player : {} in Game : {}", game.getCurrentPlayerName(), game.getGameId());
        if (game.getLastMoveStatus().equals(Game.LastMoveStatus.MANCALA)) {
            game.setCurrentPlayerName(currentPlayerGameBoard.getPlayerName());
        }
    }

    @Override
    public Integer getPriority() {
        return 0;
    }
}
