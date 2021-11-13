package com.bol.mancala.usecase;

import com.bol.mancala.adaptor.repository.GameRespository;
import com.bol.mancala.entity.model.Game;
import com.bol.mancala.entity.model.GameBoard;
import com.bol.mancala.usecase.exception.GameException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GameManagementImpl implements GameManagement {

    private static final Logger log = LoggerFactory.getLogger(GameManagementImpl.class);
    private final GameRespository gameRespository;

    @Override
    public List<Game> getAllGames() {

        log.info("get All Games");
        return gameRespository.findAll();
    }

    @Override
    public Game loadGame(String gameId) {
        log.info("load Game details for ID : {} ",gameId);
        Optional<Game> optionalGame = gameRespository.findById(gameId);
        return optionalGame.orElseThrow(() -> new GameException("Game not found"));
    }

    @Override
    public Game saveGame(Game game) {
        log.info("save Game.");
        return gameRespository.save(game);
    }

    @Override
    public Game createAndJoinGame(String player1Name, String player2Name) {
        log.info("create a new Game for Player1 : {} & Player2 : {}", player1Name, player2Name);
        Game game = new Game();
        joinGame(player1Name, player2Name, game);
        makeGamePlayable(game);
        game.setCurrentPlayerName(player1Name);
        game.setGameStatus(Game.GameStatus.IN_PROGRESS);
        return gameRespository.save(game);
    }

    private void joinGame(String player1Name, String player2Name, Game game) {
        log.info("join Game Id : {} ,for Player1 : {} & Player2 : {}", game.getGameId(), player1Name, player2Name);
        if (game.getGameBoardList().size() > 1)
            throw new GameException("game is full");
        else {
            game.getGameBoardList().add(new GameBoard(player1Name));
            game.getGameBoardList().add(new GameBoard(player2Name));
        }
    }

    private void makeGamePlayable(Game game) {
        if (game.getGameBoardList().size() == 2)
            game.setGameStatus(Game.GameStatus.PLAYABLE);
    }
}
