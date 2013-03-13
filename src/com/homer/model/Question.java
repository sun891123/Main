package com.homer.model;

import java.util.ArrayList;

public class Question {
	
	private String QuestionID;
	private String QuestionContent;
	private String Options;
	
	public ArrayList<Answer> answerList;

	public String getQuestionID() {
		return QuestionID;
	}

	public void setQuestionID(String questionID) {
		QuestionID = questionID;
	}

	public String getQuestionContent() {
		return QuestionContent;
	}

	public void setQuestionContent(String questionContent) {
		QuestionContent = questionContent;
	}

	public String getOptions() {
		return Options;
	}

	public void setOptions(String options) {
		Options = options;
	}

	public ArrayList<Answer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(ArrayList<Answer> answerList) {
		this.answerList = answerList;
	}

	
}
