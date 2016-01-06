package edu.cmu.cs.lti.discoursedb.tutorial.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple PoJo to represent rows in GitHub issue extracts in CSV format.<br/>
 * Expects files with the following header:<br/>
 * <code>rectype,issueid,project_owner,project_name,actor,time,text,action,title,provenance,plus_1,urls,issues,userref,code</code><br/>
 * This version always maps to String and does not perform any data conversion. 
 * 
 * @author Oliver Ferschke
 *
 */
public class GitHubIssueComment {
	
	private String rectype;
	private String issueid;
	private String projectOwner;
	private String projectName;
	private String actor;
	private String time;
	private String text;
	private String action;
	private String title;
	private String provenance;
	private String plusOne;
	private String urls;
	private String issues;
	private String userref;
	private String code;
	
	public GitHubIssueComment(){};
		
	public String getRectype() {
		return rectype;
	}
	public void setRectype(String rectype) {
		this.rectype = rectype;
	}
	public String getIssueid() {
		return issueid;
	}
	public void setIssueid(String issueid) {
		this.issueid = issueid;
	}
	@JsonProperty("project_owner")
	public String getProjectOwner() {
		return projectOwner;
	}
	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}
	@JsonProperty("project_name")
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProvenance() {
		return provenance;
	}
	public void setProvenance(String provenance) {
		this.provenance = provenance;
	}
	@JsonProperty("plus_1")
	public String getPlusOne() {
		return plusOne;
	}
	public void setPlusOne(String plusOne) {
		this.plusOne = plusOne;
	}
	public String getUrls() {
		return urls;
	}
	public void setUrls(String urls) {
		this.urls = urls;
	}
	public String getIssues() {
		return issues;
	}
	public void setIssues(String issues) {
		this.issues = issues;
	}
	public String getUserref() {
		return userref;
	}
	public void setUserref(String userref) {
		this.userref = userref;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
