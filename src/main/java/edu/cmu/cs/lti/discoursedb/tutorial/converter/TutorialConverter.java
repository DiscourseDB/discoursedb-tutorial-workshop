package edu.cmu.cs.lti.discoursedb.tutorial.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import edu.cmu.cs.lti.discoursedb.core.service.system.DataSourceService;
import edu.cmu.cs.lti.discoursedb.tutorial.model.GitHubIssueComment;

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

	@Autowired private DataSourceService dataSourceService;
	@Autowired private TutorialConverterService converterService;

	@Override
	public void run(String... args) throws Exception {
				
		//Parse command line param with dataset name
		final String dataSetName=args[0];		
		if(dataSourceService.dataSourceExists(dataSetName)){
			logger.warn("Dataset "+dataSetName+" has already been imported into DiscourseDB. Terminating...");			
			return;
		}
		
		//Parse command line param with dataset location
		final Path dataSetPath = Paths.get(args[1]);
		File dataSetFile = dataSetPath.toFile();
		if (!dataSetFile.exists() || dataSetFile.isFile() || !dataSetFile.canRead()) {
			logger.error("Provided location is a file and not a directory.");
			throw new RuntimeException("Can't read directory "+dataSetPath);
		}
		
		//Walk through dataset directory and parse each file
		logger.info("Start processing dataset");				
			try (Stream<Path> pathStream = Files.walk(dataSetPath)) {
				pathStream.filter(path -> path.toFile().isFile())
					 .filter(path -> !path.endsWith(".csv"))
					 .forEach(path -> processFile(path.toFile()));
			}				
    	logger.info("All done.");
	}

	/**
	 * Parses a dataset file, binds its contents to a POJO and passes it on to the DiscourseDB converter
	 * 
	 * @param file an dataset file to process
	 */
	private void processFile(File file){
		logger.info("Processing "+file);				
   		try(InputStream in = new FileInputStream(file);) {
       		CsvMapper mapper = new CsvMapper();
       		CsvSchema schema = mapper.schemaWithHeader().withNullValue("None");
       		MappingIterator<GitHubIssueComment> it = mapper.readerFor(GitHubIssueComment.class).with(schema).readValues(in);
       		while (it.hasNextValue()) {
       			GitHubIssueComment currentComment = it.next();
       			//TODO pass each data point to the converterService which then performs the mapping in a transaction
       			//don't perform the mapping here directly, since it would not allow the transactions to be
       			//work properly
       			
       		}
       	}catch(Exception e){
       		logger.error("Could not parse data file "+file, e);
       	}    		
	}

}