package com.homer.xml;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.ProtocolException;
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
	public static String userIdString;
	public static String userNamesString;
	LinearLayout linearLayout_root = null;
	RadioGroup radioGroup = null;
	private ArrayList<PostValue> postArray = null;
	EditText mEditText = null;
	private Surveys aSurvey = null;
	
	
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
		mEditText.setId(Integer.valueOf(mQuestion.getQuestionID()));
		linearLayout_root.addView(mEditText);
	}
	
	
	//��ʼ��Ϊȫ����ѡ״̬��post����
	@SuppressLint("UseSparseArrays")
	public void addPostValue(Question aQuestion) {
		PostValue temp_postPostValue = new PostValue();
		temp_postPostValue.setOptions(aQuestion.getOptions());
		temp_postPostValue.setQuestionIdString(aQuestion.getQuestionID());
		HashMap<Integer, Object> map = new HashMap<Integer, Object>();
		if (Integer.valueOf(aQuestion.getOptions()) == 2) {
			map.put(Integer.valueOf(aQuestion.getOptions()), "");
		} else {
			for (int j = 0; j < aQuestion.answerList.size(); j ++) {
				Answer aAnswer = aQuestion.answerList.get(j);
				map.put(Integer.valueOf(aAnswer.getAnswerID()), false);
			}
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
		mEditText = new EditText(context);
		
		//����ʾ��һ����ѡ��
		index = 0;
		
		//��ȡ�ʾ��������������⣬Ȼ�������ʾ
		aSurvey = (Surveys)getIntent().getSerializableExtra("survey");
		//��������࣬��ѡ���顢��ѡ���顢��������
		sigleArrayList = new ArrayList<Question>();
		doubleArrayList = new ArrayList<Question>();
		messageArrayList = new ArrayList<Question>();
		
		//���з��࣬���ҳ�ʼ���Ǽ�¼״̬����
		for (int i = 0; i < aSurvey.aQuestionList.size(); i ++) {
			Question aQuestion = aSurvey.aQuestionList.get(i);
			if (aQuestion.getOptions().equalsIgnoreCase("0")) {
				sigleArrayList.add(aQuestion);
			} else if (aQuestion.getOptions().equalsIgnoreCase("1")) {
				doubleArrayList.add(aQuestion);
			} else if (aQuestion.getOptions().equalsIgnoreCase("2")) {
				messageArrayList.add(aQuestion);
			}
		}
		
		for (int i = 0; i < sigleArrayList.size(); i ++) {
			Question aQuestion = sigleArrayList.get(i);
			addPostValue(aQuestion);
		}
		
		for (int i = 0; i < doubleArrayList.size(); i ++) {
			Question aQuestion = doubleArrayList.get(i);
			addPostValue(aQuestion);
		}
		
		for (int i = 0; i < messageArrayList.size(); i ++) {
			Question aQuestion = messageArrayList.get(i);
			addPostValue(aQuestion);
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
					int temp_index = index - (sigleArrayList.size());
					Log.e(TAG, "" + temp_index);
					Question aQuestion = doubleArrayList.get(temp_index);
					PostValue tempPostValue = postArray.get(index - 1);
					if (index == sigleArrayList.size()) {
						tempPostValue.isSelected.put(radioGroup.getCheckedRadioButtonId(), true);
					} else {
						for (int j = 0; j < aQuestion.answerList.size(); j ++) {
							Answer aAnswer = aQuestion.answerList.get(j);
							CheckBox tempcBox = (CheckBox) findViewById(Integer.valueOf(aAnswer.getAnswerID()));
							if (tempcBox.isChecked()) {
								tempPostValue.isSelected.put(Integer.valueOf(aAnswer.getAnswerID()), true);
							} else {
								tempPostValue.isSelected.put(Integer.valueOf(aAnswer.getAnswerID()), false);
							}
						}
					}
					
					//������һ��ѡ��
					createdouble(aQuestion);
				} else if (index >= (doubleArrayList.size() + sigleArrayList.size()) && index < (doubleArrayList.size() + sigleArrayList.size() + messageArrayList.size())) {
					//��������ԣ����ȼ�¼��ǰ����Ŀ��״̬��������ô�õ�editingview���ı��أ�����һ�������ǣ������ǰ��Ŀ�������ԣ����ǴӶ�ѡ���л���������ô�죿
					int temp_index = index - (sigleArrayList.size() + doubleArrayList.size());
					Log.e(TAG, "" + index + temp_index);
					PostValue tempPostValue = postArray.get(index - 1);
					Question aQuestion = messageArrayList.get(temp_index);
					if (index == (doubleArrayList.size() + sigleArrayList.size())) {
						Question tempQuestion = doubleArrayList.get(temp_index - 1);
						for (int j = 0; j < tempQuestion.answerList.size(); j ++) {
							Answer aAnswer = tempQuestion.answerList.get(j);
							CheckBox tempcBox = (CheckBox) findViewById(Integer.valueOf(aAnswer.getAnswerID()));
							if (tempcBox.isChecked()) {
								tempPostValue.isSelected.put(Integer.valueOf(aAnswer.getAnswerID()), true);
							} else {
								tempPostValue.isSelected.put(Integer.valueOf(aAnswer.getAnswerID()), false);
							}
						}
					} else {
						tempPostValue.isSelected.put(Integer.valueOf(aQuestion.getQuestionID()), mEditText.getText());
					}
					//������һ������Ŀ
					createMessage(aQuestion);
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
		} else {
			prvatButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (index != 0) {
						index --;
					}
					// TODO Auto-generated method stub
					//1������ʾ���е�ѡ�⡣Options  Ϊ0����ѡ��
					if (index < sigleArrayList.size()) {
						//����ǵ�ѡ�����ȼ�¼��ǰ����Ŀѡ��״̬
						PostValue tempPostValue = postArray.get(index + 1);
		           	 	tempPostValue.isSelected.put(radioGroup.getCheckedRadioButtonId(), true);
		           	 	//Ȼ�󴴽���һ����ѡ��
						Question mQuestion = sigleArrayList.get(index);
						createsigle(mQuestion);
					} else if (index >= sigleArrayList.size() && index < (doubleArrayList.size() + sigleArrayList.size())) {
						//����Ƕ�ѡ�����ȼ�¼��ǰ��Ŀ��ѡ��״̬��������ô�õ���ѡ״̬��checkbox�أ�����һ�������ǣ������ǰ����Ŀ���Ƕ�ѡ�����Ǵӵ�ѡ�л���������ô�죿
						int temp_index = index - (sigleArrayList.size());
						Log.e(TAG, "" + temp_index);
						Question mQuestion = doubleArrayList.get(temp_index);
						PostValue tempPostValue = postArray.get(index + 1);
						if (index == sigleArrayList.size()) {
							tempPostValue.isSelected.put(radioGroup.getCheckedRadioButtonId(), true);
						} else {
							for (int j = 0; j < mQuestion.answerList.size(); j ++) {
								Answer aAnswer = mQuestion.answerList.get(j);
								CheckBox tempcBox = (CheckBox) findViewById(Integer.valueOf(aAnswer.getAnswerID()));
								if (tempcBox.isChecked()) {
									tempPostValue.isSelected.put(Integer.valueOf(aAnswer.getAnswerID()), true);
								} else {
									tempPostValue.isSelected.put(Integer.valueOf(aAnswer.getAnswerID()), false);
								}
							}
						}
						
						//������һ��ѡ��
						createdouble(mQuestion);
					} else if (index >= (doubleArrayList.size() + sigleArrayList.size()) && index < (doubleArrayList.size() + sigleArrayList.size() + messageArrayList.size())) {
						//��������ԣ����ȼ�¼��ǰ����Ŀ��״̬��������ô�õ�editingview���ı��أ�����һ�������ǣ������ǰ��Ŀ�������ԣ����ǴӶ�ѡ���л���������ô�죿
						int temp_index = index - (sigleArrayList.size() + doubleArrayList.size());
						Question mQuestion = doubleArrayList.get(temp_index);
						PostValue tempPostValue = postArray.get(index + 1);
						if (index == (doubleArrayList.size() + sigleArrayList.size())) {
							for (int j = 0; j < mQuestion.answerList.size(); j ++) {
								Answer aAnswer = mQuestion.answerList.get(j);
								CheckBox tempcBox = (CheckBox) findViewById(Integer.valueOf(aAnswer.getAnswerID()));
								if (tempcBox.isChecked()) {
									tempPostValue.isSelected.put(Integer.valueOf(aAnswer.getAnswerID()), true);
								} else {
									tempPostValue.isSelected.put(Integer.valueOf(aAnswer.getAnswerID()), false);
								}
							}
						} else {
							tempPostValue.isSelected.put(Integer.valueOf(mQuestion.getQuestionID()), mEditText.getText());
						}
						//������һ������Ŀ
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
				StringBuffer xml = new StringBuffer(); 
		        
		        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy��MM��dd��   HH:mm:ss     ");     
		        Date   curDate   =   new   Date(System.currentTimeMillis());//��ȡ��ǰʱ��     
		        String   DateStr   =   formatter.format(curDate);    


		        xml.append("xml=<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("<Answers>").append("<IPAddress>::" + getLocalIpAddress()).append("</IPAddress>")
		        .append("<VoteTime>" + DateStr).append("</VoteTime>").append("<UserId>" + userIdString).append("</UserId>").append("<UserName>" + userNamesString).append("</UserName>")
		        .append("<SourceType>2").append("</SourceType>").append("<Surveyid>" + aSurvey.getSurveyID()).append("</Surveyid>");
				
				for (int  i = 0; i < postArray.size(); i ++) {
					PostValue resultPostValue = postArray.get(i);					
					Iterator iterator = resultPostValue.isSelected.entrySet().iterator();
					if (Integer.valueOf(resultPostValue.getOptions()) == 0) {
						//����ǵ�ѡ
						xml.append("<Question ").append("Questionid=\"" + resultPostValue.getQuestionIdString() + "\">");
						while (iterator.hasNext()) {  
							Map.Entry entry = (Map.Entry) iterator.next(); 
							Object key = entry.getKey();  
							Object val = entry.getValue();
							if ((Boolean) val) {
								xml.append("<Answer>" + key + "</Answer>");
							}
						} 
						xml.append("</Question>");
					} else if (Integer.valueOf(resultPostValue.getOptions()) == 1) {
						//����Ƕ�ѡ
						xml.append("<Question ").append("Questionid=\"" + resultPostValue.getQuestionIdString() + "\">");
						while (iterator.hasNext()) {  
							Map.Entry entry = (Map.Entry) iterator.next(); 
							Object key = entry.getKey();  
							Object val = entry.getValue();
							if ((Boolean) val) {
								xml.append("<Answer>" + key + "</Answer>");
							}
						} 
						xml.append("</Question>");
					} else if (Integer.valueOf(resultPostValue.getOptions()) == 2) {
						//���������
						xml.append("<Question ").append("Questionid=\"" + resultPostValue.getQuestionIdString() + "\">");
						while (iterator.hasNext()) {  
							Map.Entry entry = (Map.Entry) iterator.next(); 
							Object key = entry.getKey();  
							Object val = entry.getValue();
							if ((Boolean) val) {
								xml.append("<Answer" + "type=\"text\">" + ">" + key + "</Answer>");
							}
						} 
						xml.append("</Question>"); 
					} 
				}
				xml.append("</Answers>");
				byte[] xmlbyte = null;
				try {
					xmlbyte = xml.toString().getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				URL url = null;
				try {
					url = new URL("http://localhost:8080/itcast/contanctmanage.do?method=readxml");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				HttpURLConnection conn = null;
				try {
					conn = (HttpURLConnection) url.openConnection();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				conn.setConnectTimeout(6* 1000);  
				conn.setDoOutput(true);//�������  
				conn.setUseCaches(false);//��ʹ��Cache  
				try {
					conn.setRequestMethod("POST");
				} catch (ProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}              
				conn.setRequestProperty("Connection", "Keep-Alive");//ά�ֳ�����  
				conn.setRequestProperty("Charset", "UTF-8");  
				conn.setRequestProperty("Content-Length", String.valueOf(xmlbyte.length));  
				conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");  
				DataOutputStream outStream = null;
				try {
					outStream = new DataOutputStream(conn.getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				try {
					outStream.write(xmlbyte);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//����xml����  
				try {
					outStream.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				try {
					if (conn.getResponseCode() != 200) throw new RuntimeException("����urlʧ��");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				InputStream inputStream = null;
				try {
					inputStream = conn.getInputStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//��ȡ��������  				
				byte[] buffer = null;
				try {
					buffer = new byte[inputStream.available()];
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputStream.read(buffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputStream.reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//��ʱ���Ī��������ݣ����Է��ּ�������Ժ�����
				        try {
							inputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				try {
					String jsonString = new String(buffer, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					outStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	//��ȡ������IP��ַ
	public static String getLocalIpAddress() {  
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
	
}
