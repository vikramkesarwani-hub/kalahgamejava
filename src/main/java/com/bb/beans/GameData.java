package com.bb.beans;

import java.util.Map;

/**
 * @author VK
 *
 */
public class GameData {

	private int id;
	private String uri;
	private Map<Integer, Integer> status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Map<Integer, Integer> getStatus() {
		return status;
	}
	public void setStatus(Map<Integer, Integer> status) {
		this.status = status;
	}
	

	
}
