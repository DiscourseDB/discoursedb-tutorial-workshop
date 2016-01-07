package edu.cmu.cs.lti.discoursedb.tutorial.model;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CSVReaderTest {

    @Test
	public void readerTest() throws Exception{
    	
    	try(InputStream in = new FileInputStream("src/test/resources/data/githubIssueCommentTest.csv");) {
    		CsvMapper mapper = new CsvMapper();    		
    		CsvSchema schema = mapper.schemaWithHeader().withNullValue("None");
    		MappingIterator<GitHubIssueComment> it = mapper.readerFor(GitHubIssueComment.class).with(schema).readValues(in);
    		
    		int i=0;
    		while (it.hasNextValue()) {
    			GitHubIssueComment comment = it.next();
    			switch(i){
    				case 0:
    					assertEquals("issue_title",comment.getRectype());
    					assertEquals(371,comment.getIssueid());
    					assertEquals("tarbell-project",comment.getProjectOwner());
    					assertEquals("tarbell",comment.getProjectName());
    					assertEquals("ghing",comment.getActor());
    					assertEquals(new Date(1249003890000L),comment.getTime());
    					assertEquals("write",comment.getAction());
    					assertEquals("Command to open spreadsheet in browser",comment.getTitle());
    					assertEquals(false,comment.getPlusOne());
    					assertEquals("githubarchive->issue_titles",comment.getProvenance());
    					assertEquals(0,comment.getUrls().size());    					
    					assertEquals("[]",comment.getIssues());    					
    					assertEquals("It would be really convenient to be able to able to run `tarbell spreadsheet` and have the Google spreadsheet open in my default browser. I'd also love to be able to run `tarbell browser production` and be able to see the site as deployed in the `production` bucket in my browser. If these features seem desirable, I'm happy to submit a pull request. It would be nice to have a way to hook into the tarbell command to be able to add custom management commands, but this seems nontrivial since the tarbell_config.py doesn't get processed until well after the subcommands are wired up.",comment.getText());
    					break;
    				case 1:
    					assertEquals("issue_event",comment.getRectype());
    					assertEquals(371,comment.getIssueid());
    					assertEquals("tarbell-project",comment.getProjectOwner());
    					assertEquals("tarbell",comment.getProjectName());
    					assertEquals("ghing",comment.getActor());
    					assertEquals(new Date(1442012871000L),comment.getTime());
    					assertEquals("labeled",comment.getAction());
    					assertEquals("None",comment.getTitle());
    					assertEquals(false,comment.getPlusOne());
    					assertEquals("ghtorrent",comment.getProvenance());
    					assertEquals(2,comment.getUrls().size());
    					assertEquals("[{\"raw\": \"https://github.com/tarbell-project/tarbell/issues/371\", \"refstyle\": \"url\", \"parts\": [\"tarbell-project\", \"tarbell\", \"371\", \"\"]}]",comment.getIssues());    					
    					assertEquals("",comment.getText());
    					break;
    			}
    			i++;
    		}
    	}	
    }
}
