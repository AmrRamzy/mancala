package com.bol.mancala.usecase;

import com.bol.mancala.entity.dto.GameRuleDto;
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
    public void applyGameRules(GameRuleDto gameRuleDto) {

        rulesQueue.forEach(r -> r.apply(gameRuleDto));
    }
}
