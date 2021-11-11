package com.bol.mancala.adaptor.controller;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.usecase.GameManagement;
import com.bol.mancala.usecase.GamePlay;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/game")
@RestController
@CrossOrigin
public class GameRestController {

    private final GamePlay gamePlay;
    private final GameManagement gameManagement;

    @GetMapping
    public List<Game> getAllGames(){
        return gameManagement.getAllGames();
    }

    @GetMapping("/{gameId}")
    public Game getGame(@PathVariable @NotNull String gameId){
        return gameManagement.loadGame(gameId);
    }

    @PostMapping
    public Game createAndJoinGame(@RequestParam @NotNull String player1Name, @RequestParam @NotNull String player2Name){
        return gameManagement.createAndJoinGame(player1Name, player2Name);
    }

    @PatchMapping("/{gameId}/start")
    public Game startGame(@PathVariable @NotNull String gameId, @RequestParam @NotNull String playerName){
        return gamePlay.startGame(gameId, playerName);
    }

    @PatchMapping("/{gameId}/play")
    public Game play(@PathVariable @NotNull String gameId, @RequestParam @NotNull String playerName, @RequestParam @NotNull @Min(0) @Max(6) Integer index){
       return gamePlay.play(gameId, playerName, index);
    }
}
