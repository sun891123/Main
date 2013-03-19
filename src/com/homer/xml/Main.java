package com.homer.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.homer.model.Surveys;
import com.homer.util.PullxmlParser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Main extends Activity {
	
	//定义显示的List相关变量  
    ListView list;  
    ArrayAdapter<String> adapter;  
    ArrayList<Surveys> surveysEntryList; 
	
	StringBuffer stringBuffer;
	
	
    

	
	//从网络上获取实时问卷数据
	private InputStream readSurveysDataFromInternet()  
    {  
        //从网络上获取实时问卷数据  
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
	
	//从本地获取问卷数据  
	private InputStream readSurveysDataFromFile()  
    {  
        //从本地获取问卷数据  
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
		//获取问卷数据流  
        InputStream surveysStream = readSurveysDataFromFile();
        //Pull方式进行xml解析  
        PullxmlParser pullHandler = new PullxmlParser();  
        surveysEntryList = pullHandler.SurveysXmlParser(surveysStream);  
        
        //获取问卷内容
        List<String> EntryList =new ArrayList<String>();
        for (int i = 0; i < surveysEntryList.size(); i ++) {
        	EntryList.add(surveysEntryList.get(i).getSurveyName());
        }
        
        //显示问卷内容到listView上
        list = (ListView)this.findViewById(R.id.list);  
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EntryList);  
        list.setAdapter(adapter);  
        list.setOnItemClickListener(new OnItemClickListener(){ 
            @Override 
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, 
                    long arg3) {
            	//点击item跳转到答题页面
            	Intent intent = new Intent();  
            	intent.putExtra("surveys", surveysEntryList.get(arg2));
                intent.setClass(Main.this, QuestionList.class);
                //这里自定义一个类似推进的跳转效果
                startActivity(intent);
            }             
        });
	    
		//2、先显示所有单选题。Options  为0（单选框）
		
		
		//3、再显示所有多选题。Options  为1（多选框）
		
		
		//4、再显示留言题。Options  为2（文本留言）
		
		
		//5、选择一题就切换到下一题，到最后一题就提示提交答案（切换和提交按钮）
	}
}