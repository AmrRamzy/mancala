package com.bol.mancala.adaptor.repository;

import com.bol.mancala.entity.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRespository  extends MongoRepository<Player, String> {
}
