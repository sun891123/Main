package com.homer.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Surveys implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String SurveyName;
	private String SurveyID;
	public  ArrayList<Question> aQuestionList;
		
	public String getSurveyName() {
		return SurveyName;
	}
	public void setSurveyName(String surveyName) {
		SurveyName = surveyName;
	}
	public String getSurveyID() {
		return SurveyID;
	}
	public void setSurveyID(String surveyID) {
		SurveyID = surveyID;
	}
	public ArrayList<Question> getQuestionList() {
		return aQuestionList;
	}
	public void setQuestionList(ArrayList<Question> questionList) {
		aQuestionList = questionList;
	}
	
}
