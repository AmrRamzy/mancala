package com.bol.mancala.usecase;

import com.bol.mancala.adaptor.repository.PlayerRespository;
import com.bol.mancala.entity.model.Player;
import com.bol.mancala.usecase.exception.PlayerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerManagementImpl implements PlayerManagement {

    private final PlayerRespository playerRespository;

    @Override
    public List<Player> getAllPlayers() {
        return playerRespository.findAll();
    }
    @Override
    public Player getPlayer(String playerName) {
        return playerRespository.findById(playerName).orElseThrow(() -> new PlayerException("Player not found"));
    }
    @Override
    public Player createPlayer(Player player) {
        return playerRespository.insert(player);
    }


}
