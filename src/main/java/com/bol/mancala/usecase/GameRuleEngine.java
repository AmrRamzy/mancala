package com.bol.mancala.usecase;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;

public interface GameRuleEngine {

    void applyGameRules(Game game, GameBoard currentPlayerGameBoard, Integer finalIndex);
}
