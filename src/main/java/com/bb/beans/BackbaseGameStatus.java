package com.bb.beans;

/**
 * @author VK
 *
 */
public class BackbaseGameStatus {
	
private boolean finished;
	
	private String winner;

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

}
