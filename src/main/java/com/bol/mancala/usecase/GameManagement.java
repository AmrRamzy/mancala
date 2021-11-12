package com.bol.mancala.usecase;

import com.bol.mancala.entity.model.Game;

import java.util.List;

public interface GameManagement {

    /**
     * method to get all Games
     * @return list of Games
     */
    List<Game> getAllGames();

    /**
     * method to load Game by ID
     * @param gameId Game ID
     * @return Game
     */
    Game loadGame(String gameId);

    /**
     * method to save Game to DB
     * @param game Game object
     * @return Game
     */
    Game saveGame(Game game);

    /**
     * method to create a new Game with 2 players
     * @param player1Name first player name
     * @param player2Name second player name
     * @return Game
     */
    Game createAndJoinGame(String player1Name, String player2Name);
}
