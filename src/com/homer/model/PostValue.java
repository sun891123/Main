package com.homer.model;

import java.util.ArrayList;
import java.util.HashMap;

public class PostValue {
	//��������
	private String Options;
	private String questionIdString;
	public ArrayList<String> answersArray;
	public HashMap<Integer,Boolean> isSelected;

	
	
	public HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		this.isSelected = isSelected;
	}
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
