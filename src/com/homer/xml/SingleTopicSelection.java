package com.homer.xml;

import com.homer.model.Answer;
import com.homer.model.Question;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SingleTopicSelection extends Activity {
	Context context = SingleTopicSelection.this;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//这里是单选题目
		setContentView(R.id.siglement_rg_subject);
	}

	public void setUpQuestion(Question mQuestion) {
		RadioGroup radioGroup = (RadioGroup)findViewById(R.id.siglement_rg_subject);
		for (int j = 0; j < mQuestion.answerList.size(); j ++) {
			Answer aAnswer = mQuestion.answerList.get(j);
			RadioButton radioButton = new RadioButton(context);
			radioButton.setText(aAnswer.getAnswerContent());
			radioGroup.addView(radioButton);
		}
	}
}
