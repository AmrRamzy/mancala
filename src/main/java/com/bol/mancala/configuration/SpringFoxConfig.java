package com.bol.mancala.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.bol.mancala.adaptor.controller"))
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());
    }
	
	private ApiInfo apiInfo() {
		return new ApiInfo("Bol.com Mancala Game REST API", "Mancala is a Game were Each of the two players has his six pits in front of him. To the right of the six pits, each player has a larger pit. At the start of the game, there are six stones in each of the six round pits .", null, null,
				new Contact("Amr Ramzy", null, "eng.amr.kamel@gmail.com"), null, null,
				Collections.emptyList());
	}
}