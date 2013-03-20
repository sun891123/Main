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
		//这里是暂时问题的列表Activiti
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionlist);
		
		postArray = new ArrayList<PostValue>();;
		index = 0;
		
		//获取问卷传过来的所有问题，然后进行显示
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
		
		//下一题按钮的动作
		Button nextButton = (Button)findViewById(R.id.nextbutton);
		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//1、先显示所有单选题。Options  为0（单选框）
				if (index < sigleArrayList.size()) {
					Question mQuestion=sigleArrayList.get(index);
					TextView questionContentTextView = (TextView)findViewById(R.id.questioncontent);
					questionContentTextView.setText(mQuestion.getQuestionContent());
					RadioGroup radioGroup=(RadioGroup)findViewById(R.id.siglement_rg_subject);
					//进行上次选择的记录
					postValue = new PostValue();
					postValue.setOptions(mQuestion.getOptions());
					postValue.setQuestionIdString(mQuestion.getQuestionID());
					postValue.answersArray = new ArrayList<String>();
					String seletcs = String.valueOf(radioGroup.getCheckedRadioButtonId());
					postValue.answersArray.add(seletcs);
					//然后用数组来装已经选择过的题目
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
				
				
				//上一题的按钮
				Button prvatButton = (Button)findViewById(R.id.prvatebutton);
				if (index == 0) {
				} else if (index == sigleArrayList.size() + doubleArrayList.size() + messageArrayList.size()) {
					prvatButton.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							//组织post的xml，然后异步上传
						}
					});
				} else {
					prvatButton.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							index --;
							// TODO Auto-generated method stub
							//如果当前是单选的话
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
								//如果当前是多选的话
							} else if (index >= doubleArrayList.size() && index < messageArrayList.size()) {
								//如果当前是留言的话
							}
						}
					});
				}
			}
		});		
		
				
		
		
		//3、再显示所有多选题。Options  为1（多选框）
				
				
		//4、再显示留言题。Options  为2（文本留言）
				
				
		//5、选择一题就切换到下一题，到最后一题就提示提交答案（切换和提交按钮）
		
		
		//记录用什么方式？
		
		//然后把记录好的id和文本上传到服务器
		
		
		
		
		
		
		
		
		
		//6、把用户选择好的答案ID或者留言内容上传到服务器，以xml文件格式（需要组织为xml格式），提交完就返回问卷列表
//		Map<String, PostValue> map= new HashMap<String, PostValue>();
//		//这里用for循环得到数组里面的所有元素
//		PostValue quetion = new PostValue();
//		map.put("question", quetion);
//		//服务器请求路径  
//        String urlPath = "http://ip地址:8080";  
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
        //这里需要判断是否上传成功
		//6
		
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
