package com.bb.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackBaseLoggingController {

	private static final Logger logger = LoggerFactory.getLogger(BackBaseLoggingController.class);
	 
    @RequestMapping("/")
    public String index() {

    logger.info("Testing..............");
        return "Hello ...";
    }
	
}
