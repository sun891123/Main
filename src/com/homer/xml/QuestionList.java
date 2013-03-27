package com.homer.xml;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.homer.model.PostValue;
import com.homer.model.Question;
import com.homer.model.Surveys;

import android.R.bool;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.IntToString;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.homer.model.Answer;
import com.homer.model.PostValue;

public class QuestionList extends Activity {

	private static final String TAG = null;
	private int index;
	Context context = QuestionList.this;
	ArrayList<Question> sigleArrayList = null;
	ArrayList<Question> doubleArrayList = null;
	ArrayList<Question> messageArrayList = null;
	public String userIdString;
	public String userNamesString;
	LinearLayout linearLayout_root = null;
	RadioGroup radioGroup = null;
	private ArrayList<PostValue> postArray = null;
	
	//������ѡ��Ŀ�ؼ��ķ���
	public void createsigle(Question mQuestion) {
		linearLayout_root.removeAllViews();
		TextView questionContentTextView = (TextView)findViewById(R.id.questioncontent);
		questionContentTextView.setText(mQuestion.getQuestionContent());			
		radioGroup.clearCheck();
		radioGroup.removeAllViews();
		for (int j = 0; j < mQuestion.answerList.size(); j ++) {
			Answer aAnswer = mQuestion.answerList.get(j);
			RadioButton radioButton = new RadioButton(context);
			radioButton.setId(Integer.parseInt(aAnswer.getAnswerID()));
			radioButton.setSelected(false);
			radioButton.setText(aAnswer.getAnswerContent());
			radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                 public void onCheckedChanged(RadioGroup group, int checkedId) {
                	 Log.e(TAG, "" + index);
                	 
                 }
           });
			radioGroup.addView(radioButton);
		}
		linearLayout_root.addView(radioGroup);
	}
	
	//������ѡ��Ŀ�ؼ��ķ���
	public void createdouble(Question mQuestion) {
		linearLayout_root.removeAllViews();
		TextView questionContentTextView = (TextView)findViewById(R.id.questioncontent);
		questionContentTextView.setText(mQuestion.getQuestionContent());
		for (int j = 0; j < mQuestion.answerList.size(); j ++) {
			Answer aAnswer = mQuestion.answerList.get(j);
			CheckBox checkBoxButton = new CheckBox(context);
			checkBoxButton.setText(aAnswer.getAnswerContent());
			checkBoxButton.setId(Integer.parseInt(aAnswer.getAnswerID()));
			checkBoxButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
	            @Override 
	            public void onCheckedChanged(CompoundButton buttonView, 
	                    boolean isChecked) { 
	            	Log.e(TAG, "" + buttonView.getId() + isChecked);
	                // TODO Auto-generated method stub 
	                if(isChecked){ 
	                }else{ 
	                } 
	            } 
	        });
			linearLayout_root.addView(checkBoxButton);
		}
	} 
	
	//�������Կؼ��ķ���
	public void createMessage(Question mQuestion) {
		linearLayout_root.removeAllViews();
		EditText mEditText = new EditText(context);
		linearLayout_root.addView(mEditText);
	}
		
	
	//��ʼ��Ϊȫ����ѡ״̬��post����
	@SuppressLint("UseSparseArrays")
	public void addPostValue(Question aQuestion) {
		PostValue temp_postPostValue = new PostValue();
		temp_postPostValue.setOptions(aQuestion.getOptions());
		temp_postPostValue.setQuestionIdString(aQuestion.getQuestionID());
		HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		for (int j = 0; j < aQuestion.answerList.size(); j ++) {
			Answer aAnswer = aQuestion.answerList.get(j);
			map.put(Integer.valueOf(aAnswer.getAnswerID()), false);
		}
		temp_postPostValue.setIsSelected(map);
		postArray.add(temp_postPostValue);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//��������ʱ������б�Activiti
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionlist);
		
		//�õ�linearlayot
		linearLayout_root = (LinearLayout) findViewById(R.id.linearLayout_root);
		//��ʼ����ѡ��
		radioGroup = new RadioGroup(context);
		//����װ�������¼����
		postArray = new ArrayList<PostValue>();
		//����ʾ��һ����ѡ��
		index = 0;
		
		//��ȡ�ʾ��������������⣬Ȼ�������ʾ
		Surveys survey = (Surveys)getIntent().getSerializableExtra("survey");
		//��������࣬��ѡ���顢��ѡ���顢��������
		sigleArrayList = new ArrayList<Question>();
		doubleArrayList = new ArrayList<Question>();
		messageArrayList = new ArrayList<Question>();
		
		//���з��࣬���ҳ�ʼ���Ǽ�¼״̬����
		for (int i = 0; i < survey.aQuestionList.size(); i ++) {
			Question aQuestion = survey.aQuestionList.get(i);
			if (aQuestion.getOptions().equalsIgnoreCase("0")) {
				sigleArrayList.add(aQuestion);
				addPostValue(aQuestion);
			} else if (aQuestion.getOptions().equalsIgnoreCase("1")) {
				doubleArrayList.add(aQuestion);
				addPostValue(aQuestion);
			} else if (aQuestion.getOptions().equalsIgnoreCase("2")) {
				messageArrayList.add(aQuestion);
				addPostValue(aQuestion);
			}
		}
		
		//����ʾ��ѡ�ĵ�һ��
		Question mQuestion = sigleArrayList.get(index);
		this.createsigle(mQuestion);
		
		
		//��һ�ⰴť�Ķ���
		Button nextButton = (Button)findViewById(R.id.nextbutton);
		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				
				if (index == 0) {
					index ++;
				}
				// TODO Auto-generated method stub
				//1������ʾ���е�ѡ�⡣Options  Ϊ0����ѡ��
				if (index < sigleArrayList.size()) {
					//����ǵ�ѡ�����ȼ�¼��ǰ����Ŀѡ��״̬
					PostValue tempPostValue = postArray.get(index - 1);
	           	 	tempPostValue.isSelected.put(radioGroup.getCheckedRadioButtonId(), true);
	           	 	//Ȼ�󴴽���һ����ѡ��
					Question mQuestion = sigleArrayList.get(index);
					createsigle(mQuestion);
				} else if (index >= sigleArrayList.size() && index < (doubleArrayList.size() + sigleArrayList.size())) {
					//����Ƕ�ѡ�����ȼ�¼��ǰ��Ŀ��ѡ��״̬��������ô�õ���ѡ״̬��checkbox�أ�����һ�������ǣ������ǰ����Ŀ���Ƕ�ѡ�����Ǵӵ�ѡ�л���������ô�죿
					PostValue tempPostValue = postArray.get(index - 1);
					//������һ��ѡ��
					int temp_index = index - (sigleArrayList.size());
					Log.e(TAG, "" + temp_index);
					Question mQuestion = doubleArrayList.get(temp_index);
					createdouble(mQuestion);
				} else if (index >= (doubleArrayList.size() + sigleArrayList.size()) && index < (doubleArrayList.size() + sigleArrayList.size() + messageArrayList.size())) {
					//��������ԣ����ȼ�¼��ǰ����Ŀ��״̬��������ô�õ�editingview���ı��أ�����һ�������ǣ������ǰ��Ŀ�������ԣ����ǴӶ�ѡ���л���������ô�죿
					PostValue tempPostValue = postArray.get(index - 1);
					//������һ������Ŀ
					int temp_index = index - (sigleArrayList.size() + doubleArrayList.size());
					Question mQuestion = doubleArrayList.get(temp_index);
					createMessage(mQuestion);
				}
				//ѡ����󣬾�index++����ʾ��һ��׼��
				if (index != (sigleArrayList.size() + doubleArrayList.size() + messageArrayList.size() - 1)) {
					index ++;
				}
			}
		});	
		
		
		//��һ��İ�ť
		Button prvatButton = (Button)findViewById(R.id.prvatebutton);
		if (index == 0) {
			prvatButton.setVisibility(View.INVISIBLE);
		} else {
			prvatButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (index != 0) {
						index --;
					}
					// TODO Auto-generated method stub
					if (index < sigleArrayList.size()) {
						//����ǵ�ѡ�����ȼ�¼��ǰ����Ŀѡ��״̬������һ�������ǣ������ǰ�Ƕ�ѡ���л������Ļ��أ���ô�죿
						PostValue tempPostValue = postArray.get(index + 1);
		           	 	tempPostValue.isSelected.put(radioGroup.getCheckedRadioButtonId(), true);
		           	 	//������һ��ѡ��
						Question mQuestion = sigleArrayList.get(index);
						createsigle(mQuestion);
					} else if (index >= sigleArrayList.size() && index < (doubleArrayList.size() + sigleArrayList.size())) {
						//����Ƕ�ѡ�����ȼ�¼��ǰ��Ŀ��ѡ��״̬��������ô�õ���ѡ״̬��checkbox�أ�����һ�������ǣ������ǰ����Ŀ���Ƕ�ѡ�����Ǵ������л���������ô�죿
						PostValue tempPostValue = postArray.get(index - 1);
						//������һ��ѡ��
						int temp_index = index - (sigleArrayList.size());
						Log.e(TAG, "" + temp_index);
						Question mQuestion = doubleArrayList.get(temp_index);
						createdouble(mQuestion);
					} else if (index >= (doubleArrayList.size() + sigleArrayList.size()) && index < (doubleArrayList.size() + sigleArrayList.size() + messageArrayList.size())) {
						//��������ԣ����ȼ�¼��ǰ����Ŀ��״̬��������ô�õ�editingview���ı��أ�����һ�������ǣ������ǰ��Ŀ�������ԣ����ǴӶ�ѡ���л���������ô�죿
						PostValue tempPostValue = postArray.get(index - 1);
						//������һ������Ŀ
						int temp_index = index - (sigleArrayList.size() + doubleArrayList.size());
						Question mQuestion = doubleArrayList.get(temp_index);
						createMessage(mQuestion);
					}
				}
			});
		}
		
		
		//�ύ��ť
		Button finishButton = (Button)findViewById(R.id.finishbutton);
		finishButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//������֯xml�ļ�������postArray�����postvalue����
			}
		});
	}
	
	//��ȡ������IP��ַ
	public String getLocalIpAddress() {  
        try {  
            for (Enumeration<NetworkInterface> en = NetworkInterface  
                    .getNetworkInterfaces(); en.hasMoreElements();) {  
                NetworkInterface intf = en.nextElement();  
                for (Enumeration<InetAddress> enumIpAddr = intf  
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {  
                    InetAddress inetAddress = enumIpAddr.nextElement();  
                    if (!inetAddress.isLoopbackAddress()) {  
                        return inetAddress.getHostAddress().toString();  
                    }  
                }  
            }  
        } catch (SocketException ex) {  
            Log.e("WifiPreference IpAddress", ex.toString());  
        }  
        return null;  
    } 
	
	/** 
     *  
     * @param urlPath ����·�� 
     * @param params Map��keyΪ���������valueΪ���������ֵ 
     * @param encoding  ���뷽ʽ 
     * @return 
     * @throws Exception 
     */  
      
    //ͨ��post��������˷������ݣ�����÷������������  
    public static InputStream getInputStreamByPost(String urlPath,Map<String,PostValue> params,String encoding) throws Exception{  
        StringBuffer sb = new StringBuffer(); 
        
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy��MM��dd��   HH:mm:ss     ");     
        Date   curDate   =   new   Date(System.currentTimeMillis());//��ȡ��ǰʱ��     
        String   DateStr   =   formatter.format(curDate);    
       
        String userId = "<UserId>";
        String UserName = "<UserName>";
        String Surveyid = "<Surveyid>";
        String IPAddress = "<<IPAddress>::>";
        
        sb.append("xml=<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("<Answers>").append("<IPAddress>::%@").append("</IPAddress>")
        .append("<VoteTime>").append("</VoteTime>").append("<UserId>%@").append("</UserId>").append("<UserName>%@").append("</UserName>")
        .append("<SourceType>2").append("</SourceType>").append("<Surveyid>").append("</Surveyid>");
        
        for(Map.Entry<String,PostValue> entry:params.entrySet()){
        	PostValue tempPostValue = entry.getValue();
//        	if (tempPostValue.getOptions().equals("0")) {
//        		sb.append("<Question ").append("Questionid=\"%@\">").append("<Answer>").append(tempPostValue.getAnswers().indexOf(0)).append("</Answer>").append("</Question>");
//        	} else if (tempPostValue.getOptions().equals("1")) {
//        		sb.append("<Question ").append("Questionid=\"%@\">");
//				for (int i = 0; i < tempPostValue.getAnswers().size(); i ++) {
//					sb.append("<Answer>").append(tempPostValue.getAnswers().indexOf(i)).append("</Answer>");
//				}
//				sb.append("<Question ").append("</Question>");
//			} else if (tempPostValue.getOptions().equals("2")) {
//				sb.append("<Question ").append("Questionid=\"%@\">").append("<Answer ").append("type=\"text\">%@").append("</Answer>").append("</Question>");
//			}
        }
        sb.append("</Answers>");
        String data = sb.deleteCharAt(sb.length()).toString();  
        URL url = new URL(urlPath);  
        //������  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        //�����ύ��ʽ  
        conn.setDoOutput(true);  
        conn.setDoInput(true);  
        conn.setRequestMethod("POST");  
        //post��ʽ����ʹ�û���  
        conn.setUseCaches(false);  
        conn.setInstanceFollowRedirects(true);  
        //�������ӳ�ʱʱ��  
        conn.setConnectTimeout(6*1000);  
        //���ñ������ӵ�Content-Type������Ϊapplication/x-www-form-urlencoded  
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
        //ά�ֳ�����  
//        conn.setRequestProperty("Connection", "Keep-Alive");  
        //�������������  
        conn.setRequestProperty("Charset", "UTF-8");  
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());  
        //���������������������˷���  
        dos.writeBytes(data);  
        dos.flush();  
        dos.close();  
        if(conn.getResponseCode() == 200){  
            //��÷������������  
            return conn.getInputStream();  
        }  
        return null;  
    }  
      
    //ͨ������������ֽ�����  
    public static byte[] readStream(InputStream is) throws Exception {  
        byte[] buffer = new byte[1024];  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        int len = 0;  
        while((len=is.read(buffer)) != -1){  
            bos.write(buffer, 0, len);  
        }   
        is.close();  
        return bos.toByteArray();  
    }

    public String getUserIdString() {
		return userIdString;
	}
	public void setUserIdString(String userIdString) {
		this.userIdString = userIdString;
	}
	public String getUserNamesString() {
		return userNamesString;
	}
	public void setUserNamesString(String userNamesString) {
		this.userNamesString = userNamesString;
	}
}
