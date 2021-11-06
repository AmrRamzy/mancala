package com.bol.mancala.adaptor.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.bol.mancala.entity.model.Game;

public interface GameRespository extends MongoRepository<Game, String>{
}
