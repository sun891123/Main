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
	
	//创建单选题目控件的方法
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
	
	//创建多选题目控件的方法
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
	
	//创建留言控件的方法
	public void createMessage(Question mQuestion) {
		linearLayout_root.removeAllViews();
		EditText mEditText = new EditText(context);
		linearLayout_root.addView(mEditText);
	}
		
	
	//初始化为全部非选状态的post数据
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
		//这里是暂时问题的列表Activiti
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionlist);
		
		//得到linearlayot
		linearLayout_root = (LinearLayout) findViewById(R.id.linearLayout_root);
		//初始化单选组
		radioGroup = new RadioGroup(context);
		//创建装载问题记录数组
		postArray = new ArrayList<PostValue>();
		//先显示第一个单选题
		index = 0;
		
		//获取问卷传过来的所有问题，然后进行显示
		Surveys survey = (Surveys)getIntent().getSerializableExtra("survey");
		//将问题分类，单选数组、多选数组、留言数组
		sigleArrayList = new ArrayList<Question>();
		doubleArrayList = new ArrayList<Question>();
		messageArrayList = new ArrayList<Question>();
		
		//进行分类，并且初始化非记录状态数组
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
		
		//先显示单选的第一题
		Question mQuestion = sigleArrayList.get(index);
		this.createsigle(mQuestion);
		
		
		//下一题按钮的动作
		Button nextButton = (Button)findViewById(R.id.nextbutton);
		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) { 
				
				if (index == 0) {
					index ++;
				}
				// TODO Auto-generated method stub
				//1、先显示所有单选题。Options  为0（单选框）
				if (index < sigleArrayList.size()) {
					//如果是单选，我先记录当前的题目选择状态
					PostValue tempPostValue = postArray.get(index - 1);
	           	 	tempPostValue.isSelected.put(radioGroup.getCheckedRadioButtonId(), true);
	           	 	//然后创建下一个单选题
					Question mQuestion = sigleArrayList.get(index);
					createsigle(mQuestion);
				} else if (index >= sigleArrayList.size() && index < (doubleArrayList.size() + sigleArrayList.size())) {
					//如果是多选，我先记录当前题目的选择状态，这里怎么得到已选状态的checkbox呢？还有一个问题是，如果当前的题目不是多选，而是从单选切换过来的怎么办？
					PostValue tempPostValue = postArray.get(index - 1);
					//创建下一多选题
					int temp_index = index - (sigleArrayList.size());
					Log.e(TAG, "" + temp_index);
					Question mQuestion = doubleArrayList.get(temp_index);
					createdouble(mQuestion);
				} else if (index >= (doubleArrayList.size() + sigleArrayList.size()) && index < (doubleArrayList.size() + sigleArrayList.size() + messageArrayList.size())) {
					//如果是留言，我先记录当前的题目的状态，这里怎么得到editingview的文本呢？还有一个问题是：如果当前题目不是留言，而是从多选题切换过来的怎么办？
					PostValue tempPostValue = postArray.get(index - 1);
					//创建下一留言题目
					int temp_index = index - (sigleArrayList.size() + doubleArrayList.size());
					Question mQuestion = doubleArrayList.get(temp_index);
					createMessage(mQuestion);
				}
				//选择完后，就index++，表示下一题准备
				if (index != (sigleArrayList.size() + doubleArrayList.size() + messageArrayList.size() - 1)) {
					index ++;
				}
			}
		});	
		
		
		//上一题的按钮
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
						//如果是单选，我先记录当前的题目选择状态，还有一个问题是：如果当前是多选题切换过来的话呢？怎么办？
						PostValue tempPostValue = postArray.get(index + 1);
		           	 	tempPostValue.isSelected.put(radioGroup.getCheckedRadioButtonId(), true);
		           	 	//创建上一单选题
						Question mQuestion = sigleArrayList.get(index);
						createsigle(mQuestion);
					} else if (index >= sigleArrayList.size() && index < (doubleArrayList.size() + sigleArrayList.size())) {
						//如果是多选，我先记录当前题目的选择状态，这里怎么得到已选状态的checkbox呢？还有一个问题是，如果当前的题目不是多选，而是从留言切换过来的怎么办？
						PostValue tempPostValue = postArray.get(index - 1);
						//创建下一多选题
						int temp_index = index - (sigleArrayList.size());
						Log.e(TAG, "" + temp_index);
						Question mQuestion = doubleArrayList.get(temp_index);
						createdouble(mQuestion);
					} else if (index >= (doubleArrayList.size() + sigleArrayList.size()) && index < (doubleArrayList.size() + sigleArrayList.size() + messageArrayList.size())) {
						//如果是留言，我先记录当前的题目的状态，这里怎么得到editingview的文本呢？还有一个问题是：如果当前题目不是留言，而是从多选题切换过来的怎么办？
						PostValue tempPostValue = postArray.get(index - 1);
						//创建下一留言题目
						int temp_index = index - (sigleArrayList.size() + doubleArrayList.size());
						Question mQuestion = doubleArrayList.get(temp_index);
						createMessage(mQuestion);
					}
				}
			});
		}
		
		
		//提交按钮
		Button finishButton = (Button)findViewById(R.id.finishbutton);
		finishButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//这里组织xml文件，根据postArray里面的postvalue来做
			}
		});
	}
	
	//获取本机的IP地址
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
     * @param urlPath 请求路径 
     * @param params Map中key为请求参数，value为请求参数的值 
     * @param encoding  编码方式 
     * @return 
     * @throws Exception 
     */  
      
    //通过post向服务器端发送数据，并获得服务器端输出流  
    public static InputStream getInputStreamByPost(String urlPath,Map<String,PostValue> params,String encoding) throws Exception{  
        StringBuffer sb = new StringBuffer(); 
        
        SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss     ");     
        Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
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
        //打开连接  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        //设置提交方式  
        conn.setDoOutput(true);  
        conn.setDoInput(true);  
        conn.setRequestMethod("POST");  
        //post方式不能使用缓存  
        conn.setUseCaches(false);  
        conn.setInstanceFollowRedirects(true);  
        //设置连接超时时间  
        conn.setConnectTimeout(6*1000);  
        //配置本次连接的Content-Type，配置为application/x-www-form-urlencoded  
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
        //维持长连接  
//        conn.setRequestProperty("Connection", "Keep-Alive");  
        //设置浏览器编码  
        conn.setRequestProperty("Charset", "UTF-8");  
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());  
        //将请求参数数据向服务器端发送  
        dos.writeBytes(data);  
        dos.flush();  
        dos.close();  
        if(conn.getResponseCode() == 200){  
            //获得服务器端输出流  
            return conn.getInputStream();  
        }  
        return null;  
    }  
      
    //通过输入流获得字节数组  
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
