package com.homer.xml;

import com.homer.model.Question;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MultipleChoice extends Activity {


	//上一题按钮的隐藏和显示或者改变状态
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

	//6、把用户选择好的答案ID或者留言内容上传到服务器，以xml文件格式（需要组织为xml格式），提交完就返回问卷列表
//	Map<String, PostValue> map= new HashMap<String, PostValue>();
//	//这里用for循环得到数组里面的所有元素
//	PostValue quetion = new PostValue();
//	map.put("question", quetion);
//	//服务器请求路径  
//    String urlPath = "http://ip地址:8080";  
//    InputStream is = null;
//	try {
//		is = getInputStreamByPost(urlPath, map, "UTF-8");
//	} catch (Exception e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}  
//    byte[] data = null;
//	try {
//		data = readStream(is);
//	} catch (Exception e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}  
//    Log.i(TAG, new String(data));
    //这里需要判断是否上传成功
	//6
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//这里是多选
	}

	public void setUpQuestion(Question mQuestion) {
		//这里是多选题
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
