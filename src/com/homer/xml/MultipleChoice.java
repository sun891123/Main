package com.homer.xml;

import com.homer.model.Question;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MultipleChoice extends Activity {


	//��һ�ⰴť�����غ���ʾ���߸ı�״̬
//	Button prvatButton = (Button)findViewById(R.id.prvatebutton);
//	prvatButton.setVisibility(View.VISIBLE);
//	if (index == 0) {
//		prvatButton.setVisibility(View.INVISIBLE);
//	} else if (index == sigleArrayList.size() + doubleArrayList.size() + messageArrayList.size()) {
//		prvatButton.setVisibility(View.VISIBLE);
//		prvatButton.setText("@string/finish");
//	} else {
//		prvatButton.setVisibility(View.VISIBLE);
//		nextButton.setVisibility(View.INVISIBLE);
//	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//�����Ƕ�ѡ
	}

	public void setUpQuestion(Question mQuestion) {
		//�����Ƕ�ѡ��
		final CheckBox checkBoxButton = new CheckBox(null);
		checkBoxButton.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener()  
	    {  
	        @Override  
	        public void onCheckedChanged(CompoundButton buttonView,  
	                boolean isChecked) {  
	            // TODO Auto-generated method stub
	            if(checkBoxButton.isChecked())  
	            {  
	            }  
	        }  
	          
	    });
		
	}
}
