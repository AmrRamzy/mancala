package com.bol.mancala.usecase;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.rules.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

@Service
public class GameRuleEngineImpl implements GameRuleEngine {

    private final Queue<GameRule> rulesQueue;

    public GameRuleEngineImpl() {
        this.rulesQueue = new PriorityQueue<>(Comparator.comparing(GameRule::getPriority));
        this.rulesQueue.add(new CalculateGameWinnerRule());
        this.rulesQueue.add(new FinalizeGameRule());
        this.rulesQueue.add(new LastStoneInMancalaRule());
        this.rulesQueue.add(new LastStoneInSameCurrentPlayerBoardRule());
    }

    @Override
    public void applyGameRules(Game game, GameBoard currentPlayerGameBoard, Integer finalIndex) {

        rulesQueue.forEach(r -> r.apply(game, currentPlayerGameBoard, finalIndex));
    }
}
