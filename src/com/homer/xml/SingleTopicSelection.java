package com.homer.xml;

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
	}

	public void setUpQuestion(Question mQuestion) {
		RadioGroup radioGroup = new RadioGroup(context);
		//for in
		RadioButton radioButton = new RadioButton(context);
//		radioButton.setId(i);
		radioButton.setText("hahahahah");
		radioGroup.addView(radioButton);
		
	}
	
}
