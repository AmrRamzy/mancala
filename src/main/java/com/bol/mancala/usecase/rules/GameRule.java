package com.bol.mancala.usecase.rules;

import com.bol.mancala.entity.dto.GameRuleDto;

public interface GameRule {
    void apply(GameRuleDto gameRuleDto);

    Integer getPriority();
}
