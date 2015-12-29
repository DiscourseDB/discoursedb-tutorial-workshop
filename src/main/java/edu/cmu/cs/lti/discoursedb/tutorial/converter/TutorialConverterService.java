package edu.cmu.cs.lti.discoursedb.tutorial.converter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation= Propagation.REQUIRED, readOnly=false)
@Service
public class TutorialConverterService{



}