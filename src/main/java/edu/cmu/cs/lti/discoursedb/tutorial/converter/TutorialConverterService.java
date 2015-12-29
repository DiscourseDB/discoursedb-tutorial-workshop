package edu.cmu.cs.lti.discoursedb.tutorial.converter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * This class is responsible to process data chunks provided by the TutorialConverter and store them in DiscourseDB using DiscourseDB Service classes or (if necessary) repositories.
 * Each method in this class is run transactionally (each method inherits the class-level Transactional annotation)
 * 
 * @author Oliver Ferschke
 *
 */
@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
@Service
public class TutorialConverterService{



}