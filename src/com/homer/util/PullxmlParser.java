package com.homer.util;

import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.homer.model.Question;
import com.homer.model.Surveys;
import com.homer.model.Answer;

public class PullxmlParser {

	//xml解析用到的Tag  
    private String kSurveyElementName = "Survey";  
    private String kSurveyNameElementName = "SurveyName"; 
    private String kSurveyIDElementName = "SurveyID"; 
    private String kQuestionListElementName = "QuestionList";
    private String kQuestionElementName = "Question";  
    private String kQuestionIDElementName = "QuestionID";  
    private String kQuestionContentElementName = "QuestionContent";  
    private String kOptionsElementName = "Options"; 
    private String kAnswerListElementName = "AnswerList";
    private String kAnswerElementName = "Answer";
    private String kAnswerIDElementName = "AnswerID";  
    private String kAnswerContentElementName = "AnswerContent";  
    
    //用于保存xml解析获取的结果
    private Answer mAnswer = null;
    
	private Question mQuestion = null;
	
	private ArrayList<Surveys> mSurveyList = null;
	private Surveys mSurvey = null;
	private Boolean startEntryElementFlag = false;
	
	
	public ArrayList<Surveys> SurveysXmlParser(InputStream mInputStream) {
		XmlPullParserFactory pullFactory;
		XmlPullParser xmlPullParser;
		try {
			pullFactory = XmlPullParserFactory.newInstance();
			xmlPullParser = pullFactory.newPullParser();
			xmlPullParser.setInput(mInputStream, "UTF-8");

			int mEvtentType = xmlPullParser.getEventType();
			Boolean isDone = false;
			
			while ((mEvtentType != XmlPullParser.END_DOCUMENT)
					&& (isDone != true)) {
				String DocumentCode = null;
				switch (mEvtentType) {

				case XmlPullParser.START_DOCUMENT:
					mSurveyList = new ArrayList<Surveys>();
					break;
				case XmlPullParser.START_TAG:

					DocumentCode = xmlPullParser.getName();
					System.out.println("DocumentCode:" + DocumentCode);

					if (DocumentCode.equals(kSurveyElementName)) {
						Log.e("Survey=========", "Survey");
						// mSurvey = new Surveys();
						startEntryElementFlag = true;
					} else if (startEntryElementFlag == true) {
						String currentData = null;
						if (DocumentCode.equals(kSurveyNameElementName)) {
							mSurvey = new Surveys();
							currentData = xmlPullParser.nextText();
							Log.v("Pull", currentData);
							// Ã·»°Œ æÌid
							mSurvey.setSurveyName(currentData);
						} else if (DocumentCode
								.equalsIgnoreCase(kSurveyIDElementName)) {
							currentData = xmlPullParser.nextText();
							mSurvey.setSurveyID(currentData);
						} else if (DocumentCode
								.equalsIgnoreCase(kQuestionListElementName)) {
							mSurvey.aQuestionList = new ArrayList<Question>();
							break;
						} else if (DocumentCode
								.equalsIgnoreCase(kQuestionElementName)) {
							mQuestion = new Question();
							break;
						} else if (DocumentCode
								.equalsIgnoreCase(kQuestionIDElementName)) {
							currentData = xmlPullParser.nextText();
							mQuestion.setQuestionID(currentData);
						} else if (DocumentCode
								.equalsIgnoreCase(kQuestionContentElementName)) {
							currentData = xmlPullParser.nextText();
							mQuestion.setQuestionContent(currentData);
						} else if (DocumentCode
								.equalsIgnoreCase(kOptionsElementName)) {
							currentData = xmlPullParser.nextText();
							mQuestion.setOptions(currentData);
						} else if (DocumentCode
								.equalsIgnoreCase(kAnswerListElementName)) {
							mQuestion.answerList = new ArrayList<Answer>();
							break;
						} else if (DocumentCode
								.equalsIgnoreCase(kAnswerElementName)) {
							mAnswer = new Answer();
							break;
						} else if (DocumentCode
								.equalsIgnoreCase(kAnswerIDElementName)) {
							currentData = xmlPullParser.nextText();
							mAnswer.setAnswerID(currentData);
						} else if (DocumentCode
								.equalsIgnoreCase(kAnswerContentElementName)) {
							currentData = xmlPullParser.nextText();
							mAnswer.setAnswerContent(currentData);
						}
					}
				case XmlPullParser.END_TAG:
					if (xmlPullParser.getName().equals(kSurveyNameElementName)) {
						Log.e("Endd======", "End    End   End");
						System.out.println("End");
						mSurveyList.add(mSurvey);
					}
					if (xmlPullParser.getName().equalsIgnoreCase(
							kQuestionElementName)) {
						mSurvey.aQuestionList.add(mQuestion);
					}
					if (xmlPullParser.getName().equalsIgnoreCase(
							kAnswerElementName)) {
						mQuestion.answerList.add(mAnswer);
					}
					break;
				}
				mEvtentType = xmlPullParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mSurveyList;
	}
	
}
