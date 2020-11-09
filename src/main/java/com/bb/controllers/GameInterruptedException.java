package com.bb.controllers;

/**
 * @author VK
 *
 */
public class GameInterruptedException extends RuntimeException {

   
	    private static final long serialVersionUID = 1L;

	    public GameInterruptedException(String msg) {
	        super(msg);
	    }
	    
	    public GameInterruptedException(String msg,Exception cause) {
	        super(msg,cause);
	    }

}
