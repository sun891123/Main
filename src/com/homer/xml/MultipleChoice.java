package com.homer.xml;

import com.homer.model.Question;

import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MultipleChoice {


	

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
