package com.bb.service;

import com.bb.beans.GameData;

/**
 * @author VK
 *
 */
public interface BackbaseGameService {
	
	GameData createGame();
	GameData playGame(String pitId);
}

