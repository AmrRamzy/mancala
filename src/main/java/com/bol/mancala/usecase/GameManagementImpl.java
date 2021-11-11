package com.bol.mancala.usecase;

import com.bol.mancala.adaptor.repository.GameRespository;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.exception.GameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GameManagementImpl implements GameManagement {

    private final GameRespository gameRespository;

    @Override
    public List<Game> getAllGames() {
        return gameRespository.findAll();
    }

    @Override
    public Game loadGame(String gameId) {
        Optional<Game> optionalGame = gameRespository.findById(gameId);
        return optionalGame.orElseThrow(() -> new GameException("Game not found"));
    }

    @Override
    public Game saveGame(Game game) {
        return gameRespository.save(game);
    }

    @Override
    public Game createAndJoinGame(String player1Name, String player2Name) {
        Game game = new Game();
        if (game.getGameBoardMap() != null) {
            joinGame(player1Name, player2Name, game);
        } else {
            throw new GameException("could not Join Game");
        }

        makeGamePlayable(game);
        game.setCurrentPlayerName(player1Name);
        game.setGameInProgress(true);
        return gameRespository.save(game);
    }

    private void joinGame(String player1Name, String player2Name, Game game) {
        if (game.getGameBoardMap().containsKey(player1Name) || game.getGameBoardMap().containsKey(player2Name))
            throw new GameException("Player Already joined the game");
        else if (game.getGameBoardMap().size() > 0)
            throw new GameException("some player has already joined the game");
        else if (game.getGameBoardMap().size() > 1)
            throw new GameException("game is full");
        else {
            game.getGameBoardMap().put(player1Name, new GameBoard(player1Name));
            game.getGameBoardMap().put(player2Name, new GameBoard(player2Name));
        }
    }

    private void makeGamePlayable(Game game) {
        if (game.getGameBoardMap() != null && game.getGameBoardMap().size() == 2)
            game.setGamePlayable(true);
    }
}
