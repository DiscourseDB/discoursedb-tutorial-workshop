package edu.cmu.cs.lti.discoursedb.tutorial.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.cmu.cs.lti.discoursedb.configuration", "edu.cmu.cs.lti.discoursedb.io.edx.forum"})
public class TutorialConverterApplication {
	
	private static final Logger logger = LogManager.getLogger(TutorialConverterApplication.class);

	public static void main(String[] args) {
        SpringApplication.run(TutorialConverterApplication.class, args);       
	}
}
