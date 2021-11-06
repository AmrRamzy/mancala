package com.bol.mancala.usecase;

import com.bol.mancala.adaptor.repository.PlayerRespository;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class PlayerManagementImplTest {

    private PlayerRespository playerRespository;
    private PlayerManagement playerManagement;
    private Player bob;

    @BeforeEach
    void setUp() {
        bob = new Player();
        bob.setName("Bob");
        playerRespository = Mockito.mock(PlayerRespository.class);
        ArrayList playerList = new ArrayList<Player>();
        playerList.add(bob);
        Mockito.when(playerRespository.findAll()).thenReturn(playerList);
        //return same object sent to method
        Mockito.when(playerRespository.insert(any(Player.class))).thenAnswer((invocation) ->invocation.getArguments()[0]);
        Mockito.when(playerRespository.findById(anyString())).thenReturn(Optional.of(bob));
        Mockito.when(playerRespository.insert(any(Player.class))).thenAnswer((invocation) ->invocation.getArguments()[0]);
        playerManagement = new PlayerManagementImpl(playerRespository);
    }
    @Test
    void getAllPlayers() {
        List<Player> playerList = playerManagement.getAllPlayers();
        assertEquals(bob.getName(),playerList.get(0).getName());
    }
    @Test
    void getPlayer() {
        Player newPlayer = playerManagement.getPlayer("Bob");
        assertEquals(bob.getName(),newPlayer.getName());
    }
    @Test
    void createPlayer() {
        Player newPlayer = playerManagement.createPlayer(bob);
        assertEquals("Bob", bob.getName());
    }
}