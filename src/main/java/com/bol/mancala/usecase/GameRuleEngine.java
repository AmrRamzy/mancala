package com.bol.mancala.usecase;

import com.bol.mancala.entity.dto.GameRuleDto;

public interface GameRuleEngine {

    /**
     * method to apply all Game rules
     * @param gameRuleDto DTO containing Game info
     */
    void applyGameRules(GameRuleDto gameRuleDto);
}
