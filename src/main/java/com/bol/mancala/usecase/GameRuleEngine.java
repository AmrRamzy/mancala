package com.bol.mancala.usecase;

import com.bol.mancala.entity.dto.GameRuleDto;

public interface GameRuleEngine {

    void applyGameRules(GameRuleDto gameRuleDto);
}
