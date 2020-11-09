package com.bb.controllers;

import java.net.MalformedURLException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bb.beans.GameData;
import com.bb.service.BackbaseGameService;
import com.bb.service.BackbaseGameServiceImpl;

/**
 * @author VK
 *
 */
@RestController
public class BackBaseGameController {
	
	private static final Logger logger = LoggerFactory.getLogger(BackBaseGameController.class);
	
	private BackbaseGameService backbaseGameService = new BackbaseGameServiceImpl();
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST, value="/games")
	public GameData createGame() throws GameInterruptedException, MalformedURLException{
		
		logger.debug("Inside create game.......");
		GameData gameData = backbaseGameService.createGame();
	
		//Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                    .path("/{gameId}").buildAndExpand(gameData.getId()).toUri();

        gameData.setUri(location.toURL().toString());

        return gameData;
	   
	}

	
	@ResponseStatus(HttpStatus.ACCEPTED)
	@RequestMapping(method = RequestMethod.PUT, value="/games/{gameId}/pits/{pitId}")
	public GameData sortPieces(@PathVariable String pitId) throws GameInterruptedException, MalformedURLException {
				
		GameData gameData = backbaseGameService.playGame(pitId);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(gameData.getId()).toUri();
		gameData.setUri(location.toURL().toString());
        
		return gameData;
	}
}
