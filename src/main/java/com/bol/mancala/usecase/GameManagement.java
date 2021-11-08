package com.bol.mancala.usecase;

import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.Player;

import java.util.List;

public interface GameManagement {

    List<Game> getAllGames();

    Game createGame(String playerName);

    Game loadGame(String gameId);

    Game joinGame(String gameId, String playerName);

    Game joinGame(String gameId, String player1Name, String player2Name);

    Game saveGame(Game game);

    Game createAndJoinGame(String player1Name, String player2Name);
}
