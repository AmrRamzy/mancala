package com.bol.mancala.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.UUID;
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game {

    @Id
    private String gameId;
    private HashMap<String, GameBoard> gameBoardMap;
    private boolean isGamePlayable;
    private boolean isGameInProgress;
    private String currentPlayerName;
    private String gameWinnerPlayerName;

    public Game() {
        this.gameId = UUID.randomUUID().toString();
        this.gameBoardMap = new HashMap<>();
    }

}
