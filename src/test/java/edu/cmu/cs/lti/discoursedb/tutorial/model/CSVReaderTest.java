package edu.cmu.cs.lti.discoursedb.tutorial.model;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CSVReaderTest {

    @Test
	public void readerTest() throws Exception{
    	try(InputStream in = new FileInputStream("src/test/resources/data/githubIssueCommentTest.csv");) {
    		CsvMapper mapper = new CsvMapper();
    		CsvSchema schema = mapper.schemaWithHeader().withColumnSeparator(',');
    		MappingIterator<GitHubIssueComment> it = mapper.readerFor(GitHubIssueComment.class).with(schema).readValues(in);
    		
    		int i=0;
    		while (it.hasNextValue()) {
    			GitHubIssueComment comment = it.next();
    			switch(i){
    				case 0:
    					assertEquals(comment.getRectype(),"issue_title");
    					assertEquals(comment.getIssueid(),"371");
    					assertEquals(comment.getProjectOwner(),"tarbell-project");
    					assertEquals(comment.getProjectName(),"tarbell");
    					assertEquals(comment.getActor(),"ghing");
    					assertEquals(comment.getTime(),"2009-07-31 01:31:30+00:00");
    					assertEquals(comment.getAction(),"write");
    					assertEquals(comment.getTitle(),"Command to open spreadsheet in browser");
    					assertEquals(comment.getPlusOne(),"false");
    					assertEquals(comment.getProvenance(),"githubarchive->issue_titles");
    					assertEquals(comment.getUrls(),"[]");
    					assertEquals(comment.getIssues(),"[]");
    					assertEquals(comment.getText().trim(), "It would be really convenient to be able to able to run `tarbell spreadsheet` and have the Google spreadsheet open in my default browser. I'd also love to be able to run `tarbell browser production` and be able to see the site as deployed in the `production` bucket in my browser. If these features seem desirable, I'm happy to submit a pull request. It would be nice to have a way to hook into the tarbell command to be able to add custom management commands, but this seems nontrivial since the tarbell_config.py doesn't get processed until well after the subcommands are wired up.");
    					break;
    				case 1:
    					assertEquals(comment.getRectype(),"issue_event");
    					assertEquals(comment.getIssueid(),"371");
    					assertEquals(comment.getProjectOwner(),"tarbell-project");
    					assertEquals(comment.getProjectName(),"tarbell");
    					assertEquals(comment.getActor(),"ghing");
    					assertEquals(comment.getTime(),"2015-09-11 23:07:51+00:00");
    					assertEquals(comment.getAction(),"labeled");
    					assertEquals(comment.getTitle(),"None");
    					assertEquals(comment.getPlusOne(),"false");
    					assertEquals(comment.getProvenance(),"ghtorrent");
    					assertEquals(comment.getUrls(),"[]");
    					assertEquals(comment.getIssues(),"[]");    					
    					assertEquals(comment.getText(), "");
    					break;
    			}
    			i++;
    		}
    	}	
    }
}
