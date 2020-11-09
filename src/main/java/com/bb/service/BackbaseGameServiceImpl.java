package com.bb.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bb.beans.BackbasePit;
import com.bb.beans.GameData;
import com.bb.controllers.GameInterruptedException;

/**
 * @author VK
 *
 */
public class BackbaseGameServiceImpl implements BackbaseGameService {

	private static final Logger logger = LoggerFactory.getLogger(BackbaseGameServiceImpl.class);
	ArrayList<BackbasePit> pits;
	private final int gameId = 1234;
	GameData gameData = new GameData();
	
	@Override
	public GameData createGame() throws GameInterruptedException {

		int maxStones = 6;
		gameData.setId(gameId);
		pits = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			if (i == 6 || i == 13) {
				//Zero stones in Kalah
				pits.add(new BackbasePit(i, 0, "kalah", true));
			} else {
				//Stones in each pit
				pits.add(new BackbasePit(i, maxStones, "pit", false));
			}
		}
		logger.debug("Game setup complete!!");
		logger.debug("//////////////////////////simple design for coding logic/////////////////////////////");
		logger.debug("/////////////////////////////////////////////////////////////////////////////////////");
		logger.debug("///////////////////////////////////////[player1 pits]////////////////////////////////");
		logger.debug("///////////////{pit1}///{pit2}///{pit3}///{pit4}///{pit5}///{pit6}///////////////////");
		logger.debug("/////////////////////////////////////////////////////////////////////////////////////");
		logger.debug("///[Kalah14 ]///////////////////////////////////////////////////////////[Kalah7]/////");
		logger.debug("/////////////////////////////////////////////////////////////////////////////////////");
		logger.debug(" ///////////////{pit13}//{pit12}//{pit11}//{pit10}//{pit9}///{pit8}//////////////////");
		logger.debug("///////////////////////////////////////[player2 pits]////////////////////////////////");
		logger.debug("/////////////////////////////////////////////////////////////////////////////////////");
		gameData.setStatus(displayPits(pits));
		return gameData;
	}

	
	@Override
	public GameData playGame(String pitId) throws GameInterruptedException{
		
		  gameData.setId(gameId);
		  //display pits before game starts
		  displayPits(pits);
		  int playerSelectedPit = Integer.parseInt(pitId);
		  
		  //Check if player selected kalah in its move
		  if(playerSelectedPit== 7||playerSelectedPit < 1||playerSelectedPit > 13){
		      //do nothing
			  logger.debug("################Invalid Move#####################");
			  displayPits(pits);
			  throw new GameInterruptedException("################Invalid Move#####################");
			  
		  } else{
		      
		      int i=playerSelectedPit;
		      i--;
		      int k = i;
		      //Check if player selected pit having no stones to play
		      if(pits.get(k).getAmmount() == 0) {
				  logger.debug("################Sorry No stone in Pit to play#####################");
				  displayPits(pits);
		    	  throw new GameInterruptedException("################Sorry No stone in Pit to play#####################");
		      }
		      
		      //Distribute stones for player in progressing pits excluding his/her own kalah
		      while(pits.get(i).getAmmount() > 0) {
			    //remove one stone
			    pits.get(i).setAmmount(pits.get(i).getAmmount() - 1);
			    //now, need to check if it's the person's kalah or not 
		        if(k==6 && playerSelectedPit < 7){
		        	pits.get(6).setAmmount(pits.get(6).getAmmount() + 1); //adds stones to player 1's kalah
		            k++;
			         //If Last stone lands in current player own Kalah, same user will get another move to play
			         if(pits.get(i).getAmmount() == 0){
			        	 logger.debug("################Last stone lands in player own Kalah, continue to play as same user#####################");
			         }
		        } else if (k==13 && playerSelectedPit > 7){
		        	pits.get(13).setAmmount(pits.get(13).getAmmount() + 1); //adds pebble to player 2's kalah
		        	k=0; //reset count
			         //If Last stone lands in current player own Kalah, same user will get another move to play
			         if(pits.get(i).getAmmount() == 0){
			        	 logger.debug("################Last stone lands in player own Kalah, continue to play as same user#####################");
			         }

  	            }else {
  	            	if(k==13) k=0;
  	            	else {
  	            	 //add stone to progressing pit
  			         pits.get(k).setAmmount(pits.get(k).getAmmount() + 1);
  			         //if player moves end in his/her own empty pit, adjustent pit of opponent player stones and his/her own stone should move to player kalah 
  			         if(pits.get(i).getAmmount() == 0 && pits.get(k).getAmmount() == 1){
  			        	swapOppositePits(k, playerSelectedPit);
  			         }
  			         k++;}
  	            	}
		        } 
		  }
		  
		if(isGameOver()) {
			logger.debug("##################### GAME OVER #######################");
			if(pits.get(6).getAmmount() < pits.get(13).getAmmount())
				logger.debug("################# Player 2 is the winner##############");
			else
				logger.debug("################# Player 1 is the winners##############");
			
			if(pits.get(6).getAmmount() == pits.get(13).getAmmount())
				logger.debug("#####################Game Tied#######################");
			
		} else {
			logger.debug("######################Continue##########################");
		}
		gameData.setStatus(displayPits(pits));;
		return gameData;
	}
	
	
	/**
	 * Display the sorted pits/kalah with stone count.
	 * 
	 */
	public Map<Integer, Integer> displayPits(ArrayList<BackbasePit> pits){
		
		///Display of status
		 Map<Integer, Integer> retunStatus = new HashMap<Integer, Integer>();
		 int pitAmt;
		for (int i = 0; i < 14; i++) {
			pitAmt = pits.get(i).getAmmount();
			int j = i;
			retunStatus.put(Integer.valueOf(j+1), Integer.valueOf(pitAmt));
		}
		//lambda sorting
		Map<Integer, Integer> treeMap = 
				new TreeMap<>(
               (Comparator<Integer>) ( o1,  o2) -> o1.compareTo( o2)
       );
		treeMap.putAll(retunStatus);
		for (Map.Entry<Integer,Integer> entry : treeMap.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();
			logger.debug(key+":"+value);
		}
		return treeMap;
	}
	
	/**
	 * Determines whether the game has reached its end, by using isSideEmpty to
	 * check the state of each side of the board
	 */
	public boolean isGameOver() {
		return isSideEmpty();
	}

	/**
	 * Determines whether a side of the board is empty
	 */
	public boolean isSideEmpty() {
		// one of two cases needs to be true:
		// either 0-5 are empty or 7-12 are empty
		if (isBottomSideEmpty() || isTopSideEmpty()) {
		      //if one set of pits are zero.
		    collectRemainingStones(pits);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * checks if labels 0-5 are empty
	 */
	public boolean isBottomSideEmpty() {
		int counter = 0;
		for (int i = 0; i < 6; i++) {
			if (pits.get(i).getAmmount() == 0) {
				counter++;
			}
		}
		return (counter == 6); // is empty is all 6 are empty
	}

	/**
	 * checks if labels 7-12 are empty
	 */
	public boolean isTopSideEmpty() {
		int counter = 0;
		for (int i = 7; i < 13; i++) {
			if (pits.get(i).getAmmount() == 0) {
				counter++;
			}
		}
		return (counter == 6); // is empty is all 6 are empty
	}
	
	/***
	 * collects all the remaining stones 
	 *                
	 */
	public void collectRemainingStones(ArrayList<BackbasePit> pits) {
			for (int i=7;i<13;i++) {
				pits.get(13).setAmmount(pits.get(13).getAmmount() + pits.get(i).getAmmount());
				pits.get(i).setAmmount(0);
			}
			for (int i=0;i<6;i++) {
				pits.get(6).setAmmount(pits.get(6).getAmmount() + pits.get(i).getAmmount());			
				pits.get(i).setAmmount(0);
			}
	}
	
	/***
	 * collects all the remaining stones 
	 *                
	 */
	public void swapOppositePits(int index, int selectedPit) {

     	 if(selectedPit < 7 && index < 7) {
     		 
     		switch (index) {
     		 case 0:
      			pits.get(index).setAmmount(0);
      			pits.get(6).setAmmount(pits.get(6).getAmmount() + pits.get(12).getAmmount() + 1);
      			pits.get(12).setAmmount(0);
      			break;
     		case 1:
      			pits.get(index).setAmmount(0);
      			pits.get(6).setAmmount(pits.get(6).getAmmount() + pits.get(11).getAmmount() + 1);
      			pits.get(11).setAmmount(0);
      			break;
     		case 2:
      			pits.get(index).setAmmount(0);
      			pits.get(6).setAmmount(pits.get(6).getAmmount() + pits.get(10).getAmmount() + 1);
      			pits.get(10).setAmmount(0);
      			break;
     		case 3:
      			pits.get(index).setAmmount(0);
      			pits.get(6).setAmmount(pits.get(6).getAmmount() + pits.get(9).getAmmount() + 1);
      			pits.get(9).setAmmount(0);
      			break;
     		case 4:
      			pits.get(index).setAmmount(0);
      			pits.get(6).setAmmount(pits.get(6).getAmmount() + pits.get(8).getAmmount() + 1);
      			pits.get(8).setAmmount(0);
      			break;
     		case 5:
      			pits.get(index).setAmmount(0);
      			pits.get(6).setAmmount(pits.get(6).getAmmount() + pits.get(7).getAmmount() + 1);
      			pits.get(7).setAmmount(0);
      			break;
     		}
     		
      	 }else {
      		switch (index) {
      		case 7:
       			pits.get(index).setAmmount(0);
       			pits.get(13).setAmmount(pits.get(13).getAmmount() + pits.get(5).getAmmount() + 1);
       			pits.get(5).setAmmount(0);
       			break;
      		case 8:
       			pits.get(index).setAmmount(0);
       			pits.get(13).setAmmount(pits.get(6).getAmmount() + pits.get(4).getAmmount() + 1);
       			pits.get(4).setAmmount(0);
       			break;
      		case 9:
       			pits.get(index).setAmmount(0);
       			pits.get(6).setAmmount(pits.get(6).getAmmount() + pits.get(3).getAmmount() + 1);
       			pits.get(3).setAmmount(0);
       		    break;
      		case 10:
       			pits.get(index).setAmmount(0);
       			pits.get(6).setAmmount(pits.get(6).getAmmount() + pits.get(2).getAmmount() + 1);
       			pits.get(2).setAmmount(0);
       			break;
      	 	case 11:
       			pits.get(index).setAmmount(0);
       			pits.get(6).setAmmount(pits.get(6).getAmmount() + pits.get(1).getAmmount() + 1);
       			pits.get(1).setAmmount(0);
       			break;
			case 12:
       			pits.get(index).setAmmount(0);
       			pits.get(6).setAmmount(pits.get(6).getAmmount() + pits.get(0).getAmmount() + 1);
       			pits.get(0).setAmmount(0);
       			break;
       		 }
      	 }
	}
	
}
