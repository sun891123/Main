package com.homer.model;

import java.io.Serializable;

public class Answer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String AnswerID;
	private String AnswerContent;
	public String getAnswerID() {
		return AnswerID;
	}
	public void setAnswerID(String answerID) {
		AnswerID = answerID;
	}
	public String getAnswerContent() {
		return AnswerContent;
	}
	public void setAnswerContent(String answerContent) {
		AnswerContent = answerContent;
	}
}
