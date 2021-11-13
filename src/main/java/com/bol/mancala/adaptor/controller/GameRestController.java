package com.bol.mancala.adaptor.controller;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.usecase.GameManagement;
import com.bol.mancala.usecase.GamePlay;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/game")
@RestController
@CrossOrigin
@Validated
public class GameRestController {

    private static final Logger log = LoggerFactory.getLogger(GameRestController.class);
    private final GamePlay gamePlay;
    private final GameManagement gameManagement;

    @GetMapping
    @ApiOperation(value = "Get all Games", notes = "get all the Games in DB")
    public List<Game> getAllGames() {
        log.info("get all Games");
        return gameManagement.getAllGames();
    }

    @GetMapping("/{gameId}")
    @ApiOperation(value = "Get Game by ID", notes = "Get a specific Game by ID from DB")
    public Game getGame(@PathVariable @NotNull String gameId) {
        log.info("get Game details for ID : {} ", gameId);
        return gameManagement.loadGame(gameId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create and join new Game", notes = "Create a new Game and add 2 player")
    public Game createAndJoinGame(@RequestParam @NotNull String player1Name, @RequestParam @NotNull String player2Name) {
        log.info("create a new Game for Player1 : {} & Player2 : {}", player1Name, player2Name);
        return gameManagement.createAndJoinGame(player1Name, player2Name);
    }

    @PatchMapping("/{gameId}/play")
    @ApiOperation(value = "play a turn", notes = "play a turn in the Game")
    public Game play(@PathVariable @NotNull String gameId, @RequestParam @NotNull String playerName, @RequestParam @NotNull @Min(1) @Max(6) int index) {
        log.info("run a play for Player : {} in Game : {} with index : {}", playerName, gameId, index);
        return gamePlay.play(gameId, playerName, index);
    }
}
