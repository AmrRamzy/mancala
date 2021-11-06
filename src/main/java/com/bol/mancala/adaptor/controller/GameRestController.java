package com.bol.mancala.adaptor.controller;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.usecase.GameManagement;
import com.bol.mancala.usecase.GamePlay;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/game")
@RestController
public class GameRestController {

    private final GamePlay gamePlay;
    private final GameManagement gameManagement;

    @GetMapping
    public List<Game> getAllGames(){
        return gameManagement.getAllGames();
    }

    @GetMapping("/{gameId}")
    public Game getGame(@PathVariable String gameId){
        return gameManagement.loadGame(gameId);
    }

    @PostMapping
    public Game createGame(@RequestParam String playerName){
        return gameManagement.createGame(playerName);
    }

    @PatchMapping("/{gameId}")
    public Game joinGame(@PathVariable String gameId, @RequestParam String playerName){
        return gameManagement.joinGame(gameId, playerName);
    }

    @PatchMapping("/{gameId}/start")
    public Game startGame(@PathVariable String gameId, @RequestParam String playerName){
        return gamePlay.startGame(gameId, playerName);
    }

    @PatchMapping("/{gameId}/play")
    public Game play(@PathVariable String gameId, @RequestParam String playerName, @RequestParam Integer index){
       return gamePlay.play(gameId, playerName, index);
    }
}
