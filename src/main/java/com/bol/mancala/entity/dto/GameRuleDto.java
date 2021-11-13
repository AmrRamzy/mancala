package com.bol.mancala.entity.dto;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GameRuleDto {

    private Game game;
    private GameBoard currentPlayerGameBoard;
    private Integer finalIndex;
}
