package com.bol.mancala;

import com.bol.mancala.adaptor.repository.GameRespository;
import com.bol.mancala.usecase.GameManagement;
import com.bol.mancala.usecase.GamePlay;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
		MongoAutoConfiguration.class,
		MongoDataAutoConfiguration.class
})
class MancalaApplicationTests {

	@MockBean
	private GameRespository gameRespository;
	@Autowired
	private GamePlay gamePlay;
	@Autowired
	private GameManagement gameManagement;
	@Test
	void contextLoads() {
		assertNotNull(gameRespository);
		assertNotNull(gamePlay);
		assertNotNull(gameManagement);
	}

}
