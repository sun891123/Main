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
import java.util.Map;

import com.homer.model.PostValue;
import com.homer.model.Question;
import com.homer.model.Surveys;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.homer.xml.SingleTopicSelection;
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
	
	
	private ArrayList<PostValue> postArray = null;
	PostValue postValue = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//��������ʱ������б�Activiti
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionlist);
		
		postArray = new ArrayList<PostValue>();;
		index = 0;
		
		//��ȡ�ʾ��������������⣬Ȼ�������ʾ
		Surveys survey = (Surveys)getIntent().getSerializableExtra("survey");
		sigleArrayList = new ArrayList<Question>();
		doubleArrayList = new ArrayList<Question>();
		messageArrayList = new ArrayList<Question>();
		for (int i = 0; i < survey.aQuestionList.size(); i ++) {
			Question aQuestion = survey.aQuestionList.get(i);
			if (aQuestion.getOptions().equalsIgnoreCase("0")) {
				sigleArrayList.add(aQuestion);
			} else if (aQuestion.getOptions().equalsIgnoreCase("1")) {
				doubleArrayList.add(aQuestion);
			} else if (aQuestion.getOptions().equalsIgnoreCase("2")) {
				messageArrayList.add(aQuestion);
			}
		}
		
		//��һ�ⰴť�Ķ���
		Button nextButton = (Button)findViewById(R.id.nextbutton);
		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//1������ʾ���е�ѡ�⡣Options  Ϊ0����ѡ��
				if (index < sigleArrayList.size()) {
					Question mQuestion=sigleArrayList.get(index);
					TextView questionContentTextView = (TextView)findViewById(R.id.questioncontent);
					questionContentTextView.setText(mQuestion.getQuestionContent());
					RadioGroup radioGroup=(RadioGroup)findViewById(R.id.siglement_rg_subject);
					//�����ϴ�ѡ��ļ�¼
					postValue = new PostValue();
					postValue.setOptions(mQuestion.getOptions());
					postValue.setQuestionIdString(mQuestion.getQuestionID());
					postValue.answersArray = new ArrayList<String>();
					String seletcs = String.valueOf(radioGroup.getCheckedRadioButtonId());
					postValue.answersArray.add(seletcs);
					//Ȼ����������װ�Ѿ�ѡ�������Ŀ
					postArray.add(postValue);
					radioGroup.clearCheck();
					radioGroup.removeAllViews();
					for (int j = 0; j < mQuestion.answerList.size(); j ++) {
						Answer aAnswer = mQuestion.answerList.get(j);
						RadioButton radioButton = new RadioButton(context);
						radioButton.setId(j);
						radioButton.setText(aAnswer.getAnswerContent());
						radioGroup.addView(radioButton);
					}
				} else if (index >= sigleArrayList.size() && index < doubleArrayList.size()) {
				} else if (index >= doubleArrayList.size() && index < messageArrayList.size()) {
				}
				index ++;
				
				
				//��һ��İ�ť
				Button prvatButton = (Button)findViewById(R.id.prvatebutton);
				if (index == 0) {
				} else if (index == sigleArrayList.size() + doubleArrayList.size() + messageArrayList.size()) {
					prvatButton.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							//��֯post��xml��Ȼ���첽�ϴ�
						}
					});
				} else {
					prvatButton.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							index --;
							// TODO Auto-generated method stub
							//�����ǰ�ǵ�ѡ�Ļ�
							if (index < sigleArrayList.size()) {
								RadioGroup radioGroup=(RadioGroup)findViewById(R.id.siglement_rg_subject);
								radioGroup.removeAllViews();
								Question mQuestion=sigleArrayList.get(index);
								for (int j = 0; j < mQuestion.answerList.size(); j ++) {
									Answer aAnswer = mQuestion.answerList.get(j);
									RadioButton radioButton = new RadioButton(context);
									radioButton.setText(aAnswer.getAnswerContent());
									radioGroup.addView(radioButton);
								}
							} else if (index >= sigleArrayList.size() && index < doubleArrayList.size()) {
								//�����ǰ�Ƕ�ѡ�Ļ�
							} else if (index >= doubleArrayList.size() && index < messageArrayList.size()) {
								//�����ǰ�����ԵĻ�
							}
						}
					});
				}
			}
		});		
		
				
		
		
		//3������ʾ���ж�ѡ�⡣Options  Ϊ1����ѡ��
				
				
		//4������ʾ�����⡣Options  Ϊ2���ı����ԣ�
				
				
		//5��ѡ��һ����л�����һ�⣬�����һ�����ʾ�ύ�𰸣��л����ύ��ť��
		
		
		//��¼��ʲô��ʽ��
		
		//Ȼ��Ѽ�¼�õ�id���ı��ϴ���������
		
		
		
		
		
		
		
		
		
		//6�����û�ѡ��õĴ�ID�������������ϴ�������������xml�ļ���ʽ����Ҫ��֯Ϊxml��ʽ�����ύ��ͷ����ʾ��б�
//		Map<String, PostValue> map= new HashMap<String, PostValue>();
//		//������forѭ���õ��������������Ԫ��
//		PostValue quetion = new PostValue();
//		map.put("question", quetion);
//		//����������·��  
//        String urlPath = "http://ip��ַ:8080";  
//        InputStream is = null;
//		try {
//			is = getInputStreamByPost(urlPath, map, "UTF-8");
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}  
//        byte[] data = null;
//		try {
//			data = readStream(is);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}  
//        Log.i(TAG, new String(data));
        //������Ҫ�ж��Ƿ��ϴ��ɹ�
		//6
		
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
