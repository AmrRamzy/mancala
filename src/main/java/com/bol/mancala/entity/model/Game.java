package com.bol.mancala.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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
    private ArrayList<GameBoard> gameBoardList;
    private GameStatus gameStatus;
    private String currentPlayerName;
    private String gameWinnerPlayerName;
    private LastMoveStatus lastMoveStatus;
    public Game() {
        this.gameId = UUID.randomUUID().toString();
        this.gameBoardList = new ArrayList<>();
        this.gameStatus = GameStatus.INITIALIZED;
    }

    public enum GameStatus {
        INITIALIZED,
        PLAYABLE,
        IN_PROGRESS,
        GAME_OVER
    }

    public enum LastMoveStatus {
        OTHER_BOARD,
        CURRENT_BOARD,
        MANCALA
    }

}
