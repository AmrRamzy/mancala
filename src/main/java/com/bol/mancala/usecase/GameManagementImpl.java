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
    public List<Game> getAllGames(){
        return gameRespository.findAll();
    }

    @Override
    public Game createGame(String playerName) {
        Game game = new Game(playerName);
        return gameRespository.insert(game);
    }
    @Override
    public Game loadGame(String gameId) {
        Optional<Game> optionalGame = gameRespository.findById(gameId);
        return optionalGame.orElseThrow(() -> new GameException("Game not found"));
    }
    @Override
    public Game joinGame(String gameId, String playerName) {
        Game game = loadGame(gameId);
        if(game.getGameBoardMap() !=null){
            if(game.getGameBoardMap().containsKey(playerName))
                throw new GameException("Player Already joined the game");
            else if(game.getGameBoardMap().size()>1)
                throw new GameException("Game is Full");
            else
                game.getGameBoardMap().put(playerName, new GameBoard(playerName));
        }else{
            throw new GameException("could not Join Game");
        }

        makeGamePlayable(game);
        return gameRespository.save(game);
    }

    @Override
    public Game saveGame(Game game) {
        return gameRespository.save(game);
    }

    private void makeGamePlayable(Game game) {
        if(game.getGameBoardMap()!=null && game.getGameBoardMap().size()==2)
            game.setGamePlayable(true);
    }
}
