package com.bol.mancala.usecase.rules;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;

public interface GameRule {
    void apply(Game game, GameBoard currentPlayerGameBoard, Integer finalIndex);
    Integer getPriority();
}
