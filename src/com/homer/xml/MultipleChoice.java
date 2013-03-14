package com.homer.xml;

import com.homer.model.Question;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MultipleChoice extends Activity {


	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public void setUpQuestion(Question mQuestion) {
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
