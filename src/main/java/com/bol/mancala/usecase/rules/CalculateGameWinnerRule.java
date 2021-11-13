package com.bol.mancala.usecase.rules;

import com.bol.mancala.entity.dto.GameRuleDto;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class CalculateGameWinnerRule implements GameRule {

    private static final Logger log = LoggerFactory.getLogger(CalculateGameWinnerRule.class);

    @Override
    public void apply(GameRuleDto gameRuleDto) {
        log.info("execute rule for gameRuleDto : {}", gameRuleDto);

        if(gameRuleDto==null || gameRuleDto.getGame()==null)
            return ;

        Game game = gameRuleDto.getGame();
        if (game.getGameStatus().equals(Game.GameStatus.GAME_OVER)) {
            GameBoard winnerPlayerGameBoard = game.getGameBoardList()
                    .stream()
                    .max(Comparator.comparing(GameBoard::getMancala))
                    .orElseThrow(NoSuchElementException::new);
            game.setGameWinnerPlayerName(winnerPlayerGameBoard.getPlayerName());
        }

    }

    @Override
    public Integer getPriority() {
        return 100;
    }
}
