package com.bb.beans;

/**
 * @author VK
 *
 */

public class BackbasePit {

	private int seq;
	
	private int ammount;
	
	private boolean main;
	
	private String player;
	
	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public BackbasePit(){
		
	}
	
	public BackbasePit(int seq, int ammount, String player, boolean larger){
		this.seq = seq;
		this.ammount = ammount;
		this.main = larger;
		this.player = player;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getAmmount() {
		return ammount;
	}

	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}
}
