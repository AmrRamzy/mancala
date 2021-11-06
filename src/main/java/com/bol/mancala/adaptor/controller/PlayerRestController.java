package com.bol.mancala.adaptor.controller;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.Player;
import com.bol.mancala.usecase.GameManagement;
import com.bol.mancala.usecase.GamePlay;
import com.bol.mancala.usecase.PlayerManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/player")
@RestController
public class PlayerRestController {

    private final PlayerManagement playerManagement;

//    @GetMapping
//    public List<Player> getAllPlayers(){
//        return playerManagement.getAllPlayers();
//    }
//
//    @GetMapping("/{playerName}")
//    public Player getPlayer(@PathVariable String playerName){
//        return playerManagement.getPlayer(playerName);
//    }
//
//    @PostMapping
//    public Player createPlayer(@RequestBody Player player){
//       return playerManagement.createPlayer(player);
//    }
}
