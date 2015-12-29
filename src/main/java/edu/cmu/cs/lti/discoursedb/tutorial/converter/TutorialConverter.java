package edu.cmu.cs.lti.discoursedb.tutorial.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class TutorialConverter implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(TutorialConverter.class);	

	@Autowired private TutorialConverterService converterService;

	@Override
	public void run(String... args) throws Exception {

		
		logger.info("All done.");
	}



}