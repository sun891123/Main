package com.homer.model;

import java.util.ArrayList;

public class PostValue {
	private String QuetionsID;
	private String Options;
	private ArrayList<String> Answers;
	public String getQuetionsID() {
		return QuetionsID;
	}
	public void setQuetionsID(String quetionsID) {
		QuetionsID = quetionsID;
	}
	public String getOptions() {
		return Options;
	}
	public void setOptions(String options) {
		Options = options;
	}
	public ArrayList<String> getAnswers() {
		return Answers;
	}
	public void setAnswers(ArrayList<String> answers) {
		Answers = answers;
	}

	
	
}
