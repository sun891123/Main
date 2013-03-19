package com.homer.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Surveys implements Parcelable {
	
	private String SurveyName;
	private String SurveyID;
	public  ArrayList<Question> aQuestionList;
	
	public int describeContents() {
        return 0;
    }
	
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
	}

    public static final Parcelable.Creator<Surveys> CREATOR
            = new Parcelable.Creator<Surveys>() {
        public Surveys createFromParcel(Parcel in) {
            return new Surveys(in);
        }

        public Surveys[] newArray(int size) {
            return new Surveys[size];
        }
    };
    
    public Surveys(Parcel in) {
    }
	
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
