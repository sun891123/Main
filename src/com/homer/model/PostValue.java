package com.homer.model;

import java.util.ArrayList;

public class PostValue {
	//��������
	private String Options;
	private String questionIdString;
	public ArrayList<String> answersArray;
	public String getOptions() {
		return Options;
	}
	public void setOptions(String options) {
		Options = options;
	}
	public String getQuestionIdString() {
		return questionIdString;
	}
	public void setQuestionIdString(String questionIdString) {
		this.questionIdString = questionIdString;
	}
	public ArrayList<String> getAnswersArray() {
		return answersArray;
	}
	public void setAnswersArray(ArrayList<String> answersArray) {
		this.answersArray = answersArray;
	}
	
}
