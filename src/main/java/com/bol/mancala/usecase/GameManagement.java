package com.bol.mancala.usecase;

import com.bol.mancala.entity.model.Game;

import java.util.List;

public interface GameManagement {

    List<Game> getAllGames();

    Game loadGame(String gameId);

    Game saveGame(Game game);

    Game createAndJoinGame(String player1Name, String player2Name);
}
