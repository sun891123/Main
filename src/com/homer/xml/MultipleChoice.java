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

	//6�����û�ѡ��õĴ�ID�������������ϴ�������������xml�ļ���ʽ����Ҫ��֯Ϊxml��ʽ�����ύ��ͷ����ʾ��б�
//	Map<String, PostValue> map= new HashMap<String, PostValue>();
//	//������forѭ���õ��������������Ԫ��
//	PostValue quetion = new PostValue();
//	map.put("question", quetion);
//	//����������·��  
//    String urlPath = "http://ip��ַ:8080";  
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
    //������Ҫ�ж��Ƿ��ϴ��ɹ�
	//6
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
