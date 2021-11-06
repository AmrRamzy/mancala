package com.bol.mancala.usecase;

import com.bol.mancala.entity.model.Game;

public interface GamePlay {
    Game startGame(String gameId, String playerName);
    Game play(String gameId, String playerName, int currentIndex);
}
