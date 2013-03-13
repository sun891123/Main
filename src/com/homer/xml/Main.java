package com.homer.xml;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.homer.model.PostValue;
import com.homer.model.Question;
import com.homer.model.Surveys;
import com.homer.util.PullxmlParser;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



import java.util.Enumeration;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;


public class Main extends Activity {

	private static final String TAG = null;
	private TextView tvXMLCreate;
	private TextView tvXMLResolve;
	
	
	//定义显示的List相关变量  
    ListView list;  
    ArrayAdapter<Surveys> adapter;  
    ArrayList<Surveys> surveysEntryList; 
	
	StringBuffer stringBuffer;
	
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
        	if (tempPostValue.getOptions().equals("0")) {
        		sb.append("<Question ").append("Questionid=\"%@\">").append("<Answer>").append(tempPostValue.getAnswers().indexOf(0)).append("</Answer>").append("</Question>");
        	} else if (tempPostValue.getOptions().equals("1")) {
        		sb.append("<Question ").append("Questionid=\"%@\">");
				for (int i = 0; i < tempPostValue.getAnswers().size(); i ++) {
					sb.append("<Answer>").append(tempPostValue.getAnswers().indexOf(i)).append("</Answer>");
				}
				sb.append("<Question ").append("</Question>");
			} else if (tempPostValue.getOptions().equals("2")) {
				sb.append("<Question ").append("Questionid=\"%@\">").append("<Answer ").append("type=\"text\">%@").append("</Answer>").append("</Question>");
			}
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
	
	//从网络上获取实时地震数据
	private InputStream readSurveysDataFromInternet()  
    {  
        //从网络上获取实时地震数据  
        URL infoUrl = null;  
        InputStream inStream = null;  
        try {  
            infoUrl = new URL("http://earthquake.usgs.gov/earthquakes/catalogs/1day-M2.5.xml");  
            URLConnection connection = infoUrl.openConnection();  
            HttpURLConnection httpConnection = (HttpURLConnection)connection;  
            int responseCode = httpConnection.getResponseCode();  
            if(responseCode == HttpURLConnection.HTTP_OK)  
            {  
                inStream = httpConnection.getInputStream();  
            }  
        } catch (MalformedURLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return inStream;  
    }  
	
	//从本地获取地震数据  
	private InputStream readSurveysDataFromFile()  
    {  
        //从本地获取地震数据  
        InputStream inStream = null;  
        try {  
            inStream = this.getAssets().open("test.xml");  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return inStream;  
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//1、这里根据问卷的数量用列表显示，点击列表某一行进入某一个问卷，然后进行选择
		//获取地震数据流  
        InputStream surveysStream = readSurveysDataFromFile();  
        //Pull方式进行xml解析  
        PullxmlParser pullHandler = new PullxmlParser();  
        surveysEntryList = pullHandler.SurveysXmlParser(surveysStream);  
        
        
        for (int i = 0; i < surveysEntryList.size(); i ++) {
        	Surveys temp = surveysEntryList.get(i);
        	String tttString =  temp.getSurveyID();
        	String aaaString =  temp.getSurveyName();
        }
        //这里出现多个解析错误
        
        
        //用ListView进行显示  
        list = (ListView)this.findViewById(R.id.list);  
        adapter = new ArrayAdapter<Surveys>(this, android.R.layout.simple_list_item_1, surveysEntryList);  
        list.setAdapter(adapter);  
	    //实现显示功能
	    
		//2、先显示所有单选题。Options  为0（单选框）
		
		
		//3、再显示所有多选题。Options  为1（多选框）
		
		
		//4、再显示留言题。Options  为2（文本留言）
		
		
		//5、选择一题就切换到下一题，到最后一题就提示提交答案（切换和提交按钮）
		
		
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
        
        
        
        
//		tvXMLCreate = (TextView) findViewById(R.id.tvXMLCreate);
//		tvXMLResolve = (TextView) findViewById(R.id.tvXMLResolve);
//		
//		
//		
//		PullxmlParser pp = new PullxmlParser();
//		InputStream mInputStream = null;
//		ArrayList<Surveys> mArrayList = null;
//		Surveys surveys = null;
//		
//		ArrayList<Question> mArrayList2 = null;
//		Question qt = null;
//		
//		StringBuffer sb = new StringBuffer();
//		try {
//			mInputStream = this.getAssets().open("test.xml");
//			mArrayList2 = pp.XmlParser(mInputStream);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		PullxmlParser pp = new PullxmlParser();
//		InputStream mInputStream = null;
//		ArrayList<Question> mArrayList = null;
//		Question qt = null;
//		StringBuffer sb = new StringBuffer();
//		try {
//			mInputStream = this.getAssets().open("test.xml");
//			mArrayList = pp.XmlParser(mInputStream);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		for (int i = 0; i < mArrayList2.size(); i++) {
//			qt = mArrayList2.get(i);
//			sb.append(qt.getQuestionID()).append(qt.getQuestionContent())
//			.append(qt.getOptions());
//		}

//		String xml = "";
//		 XMLDom xmlDom = new XMLDom(this);
//		 xml = xmlDom.domCreateXML();
//		 tvXMLCreate.setText(xml);
		//
		// xml = xmlDom.domResolveXML();
		// tvXMLResolve.setText(xml);

		// XMLSax xmlSax = new XMLSax(this);
		// xml = xmlSax.saxCreateXML();
		// tvXMLCreate.setText(xml);
		//
		// xml = xmlSax.saxResolveXML();
		// tvXMLResolve.setText(xml);

//		XMLPull xmlPull = new XMLPull(this);
//		xml = xmlPull.pullXMLCreate();
//		tvXMLCreate.setText(xml);
//
//		xml = xmlPull.pullXMLResolve();
//		tvXMLResolve.setText(sb);
	}
}