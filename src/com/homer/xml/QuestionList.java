package com.homer.xml;

import java.nio.channels.SelectableChannel;

import com.homer.model.Surveys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QuestionList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qtaslist);
		TextView tx = (TextView) this.findViewById(R.id.txView);
		tx.setText("我来到答题界面了");
	}

}
