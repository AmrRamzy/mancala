package com.bol.mancala.usecase;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.Player;

import java.util.List;

public interface PlayerManagement {
    List<Player> getAllPlayers();
    Player createPlayer(Player player);
    Player getPlayer(String playerName);


}
