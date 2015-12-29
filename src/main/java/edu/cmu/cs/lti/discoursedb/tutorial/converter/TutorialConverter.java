package edu.cmu.cs.lti.discoursedb.tutorial.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * This component will be discovered by the starter class <code>TutorialConverterApplication</code>.<br/>
 * Since this class implements CommandLineRuner, the <code>run</code> method will receive the args of the main method of the starter class.<br/>
 * 
 * The Order annotations is not necessary. It allows to specify the order of execution in case we have multiple components.
 * This class can directly access any DiscourseDB repositories and services by Autowiring them, but it is recommended to wrap all interactions in methods located in teh <code>TutorialConverterSetrvice</code> class, which is already autowired in this stub.
 * The main reason for this is that all calls to service methods will run in separate transactions.
 * 
 * @author Oliver Ferschke
 */
@Component
@Order(1)
public class TutorialConverter implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(TutorialConverter.class);	

	@Autowired private TutorialConverterService converterService;

	@Override
	public void run(String... args) throws Exception {
		
		/*
		 * Load data set and pass on data chunks to the converter service
		 */
		
		logger.info("All done.");
	}



}