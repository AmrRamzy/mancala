package com.bol.mancala.usecase;

import com.bol.mancala.entity.model.Game;

public interface GamePlay {
    /**
     * method to play the game by moving stone from specific index
     * @param gameId Game ID
     * @param playerName Name of the player playing the turn
     * @param currentIndex index to move stones from
     * @return Game
     */
    Game play(String gameId, String playerName, int currentIndex);
}
