package edu.cmu.cs.lti.discoursedb.tutorial.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.cmu.cs.lti.discoursedb.core.model.macro.Content;
import edu.cmu.cs.lti.discoursedb.core.model.macro.Contribution;
import edu.cmu.cs.lti.discoursedb.core.model.macro.Discourse;
import edu.cmu.cs.lti.discoursedb.core.model.macro.DiscoursePart;
import edu.cmu.cs.lti.discoursedb.core.model.system.DataSourceInstance;
import edu.cmu.cs.lti.discoursedb.core.model.user.User;
import edu.cmu.cs.lti.discoursedb.core.service.macro.ContentService;
import edu.cmu.cs.lti.discoursedb.core.service.macro.ContributionService;
import edu.cmu.cs.lti.discoursedb.core.service.macro.DiscoursePartService;
import edu.cmu.cs.lti.discoursedb.core.service.macro.DiscourseService;
import edu.cmu.cs.lti.discoursedb.core.service.system.DataSourceService;
import edu.cmu.cs.lti.discoursedb.core.service.user.UserService;
import edu.cmu.cs.lti.discoursedb.core.type.ContributionTypes;
import edu.cmu.cs.lti.discoursedb.core.type.DataSourceTypes;
import edu.cmu.cs.lti.discoursedb.core.type.DiscoursePartRelationTypes;
import edu.cmu.cs.lti.discoursedb.core.type.DiscoursePartTypes;
import edu.cmu.cs.lti.discoursedb.tutorial.model.GitHubIssueComment;

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
	@Autowired  private DiscourseService discourseService;
	@Autowired  private DiscoursePartService discoursePartService;
	@Autowired  private ContentService contentService;
	@Autowired  private ContributionService contributionService;
	@Autowired  private UserService userService;
	@Autowired  private DataSourceService dataSourceService;
	
	public void mapIssue(String repoName, DiscoursePart issueDP, GitHubIssueComment p) { 
		Discourse curDiscourse = discourseService.createOrGetDiscourse("Github");
		DiscoursePart projectDP = discoursePartService.createOrGetTypedDiscoursePart(curDiscourse, 
				repoName, 
				DiscoursePartTypes.GITHUB_REPO);
		discoursePartService.createDiscoursePartRelation(projectDP, issueDP, DiscoursePartRelationTypes.SUBPART);
	}

	public long mapIssueComment(GitHubIssueComment currentComment) {
		Discourse curDiscourse = discourseService.createOrGetDiscourse("Github");
		
		String repoName = currentComment.getProjectOwner() + "/" + currentComment.getProjectName();
		
		/*
		 * Naming the issue as owner/project: #issue id
		 *   so that we can look it up and be sure of getting the same one every time.
		 *   
		 *   TODO: MEDIUM CHALLENGE: Name issues like "#<issue id>: Title".
		 *       Make sure that two issues from different projects with the same number and title are
		 *       distinguished.  This means somehow testing that they are parts of different repositories;
		 *       you may need to add or use a different method of DiscoursePartService
		 */
		String issueName = repoName + ": #" + currentComment.getIssueid();
		DiscoursePart issueDP = discoursePartService.createOrGetTypedDiscoursePart(curDiscourse, 
				issueName, DiscoursePartTypes.GITHUB_ISSUE);
		
		String actorname = currentComment.getActor();
		if (actorname == null) { actorname = "unknown"; }
		User actor = userService.createOrGetUser(curDiscourse, actorname);
		
		/*
		 * TODO: MEDIUM CHALLENGE: Set the start and end time fields in the Issue discourse part
		 * 		Find the smallest and largest time in the issue comments.  You could save this information
		 *      and set the fields after a whole issue has been imported; or you could continuously update
		 *      the issueDP object if a comment is added outside its current time range.
		 *
		 */
		
		
		switch (currentComment.getRectype()) {
		
		/*
		 * TODO: MEDIUM CHALLENGE: Handle other rectypes in the issue and commit input files.
		 *      Some of these are textual comments, but others are events that affect the state of
		 *      the discourse.  
		 */
		
		case "issue_title": {
			mapIssue(repoName, issueDP, currentComment);
			Content k = contentService.createContent();	
			k.setAuthor(actor);
			k.setStartTime(currentComment.getTime());
			
			if (currentComment.getTitle() != null && currentComment.getTitle().length() > 255) {
				k.setTitle(currentComment.getTitle().substring(0, 254));
			} else {
				k.setTitle(currentComment.getTitle());
			}
			
			k.setText(currentComment.getText());
			Contribution co = contributionService.createTypedContribution(ContributionTypes.THREAD_STARTER);
			co.setCurrentRevision(k);
			co.setFirstRevision(k);
			co.setStartTime(currentComment.getTime());
			
			dataSourceService.addSource(co, new DataSourceInstance(
					currentComment.getProjectOwner() + "/" + currentComment.getProjectName() + "#" + currentComment.getIssueid(), 
					"owner/project#issue", DataSourceTypes.GITHUB, "GITHUB"));
			discoursePartService.addContributionToDiscoursePart(co, issueDP);
			return co.getId();
		}
		
		case "issue_comment": {
			Content k = contentService.createContent();	
			k.setAuthor(actor);
			k.setStartTime(currentComment.getTime());
			k.setText(currentComment.getText());
			k.setTitle(currentComment.getTitle());
			Contribution co = contributionService.createTypedContribution(ContributionTypes.POST);
			co.setCurrentRevision(k);
			co.setFirstRevision(k);
			co.setStartTime(currentComment.getTime());
			
			discoursePartService.addContributionToDiscoursePart(co, issueDP);
			
			/*
			 * TODO: EASY CHALLENGE: Add an annotation for each URL referenced in the comment 
			 *     (use the currentComment.getUrls())
			 */
			
			/*
			 * TODO: HARD CHALLENGE: Represent the currentComment.issues() information
			 *      This field contains a JSON record for each reference to another issue; it 
			 *      should probably be translated to a DiscourseRelation.  The JSON will have to
			 *      be parsed (its sublist called "parse" lists the owner, project, and issue #
			 *      being referenced); and the referred-to issue will have to be located.
			 */
			
			
			return co.getId();
		}

		}
		

		return 0L;
	}

}