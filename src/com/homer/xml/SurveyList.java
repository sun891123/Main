package com.homer.xml;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.homer.model.Surveys;
import com.homer.util.PullxmlParser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SurveyList extends Activity {

	//������ʾ��List��ر���  
    ListView list;  
    ArrayAdapter<String> adapter;  
    ArrayList<Surveys> surveysEntryList; 
	
	StringBuffer stringBuffer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.surveyslist);
		
		//1����������ʾ���������б���ʾ������б�ĳһ�н���ĳһ���ʾ�Ȼ�����ѡ��
		//��ȡ�ʾ�������  
		InputStream surveysStream = readSurveysDataFromFile();
        //Pull��ʽ����xml����  
        PullxmlParser pullHandler = new PullxmlParser();  
        surveysEntryList = pullHandler.SurveysXmlParser(surveysStream);  
        
        //��ȡ�ʾ�����
        List<String> EntryList =new ArrayList<String>();
        for (int i = 0; i < surveysEntryList.size(); i ++) {
        	EntryList.add(surveysEntryList.get(i).getSurveyName());
        }
        
        //��ʾ�ʾ����ݵ�listView��
        list = (ListView)findViewById(R.id.list);  
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, EntryList);  
        list.setAdapter(adapter);  
        list.setOnItemClickListener(new OnItemClickListener(){ 
            @Override 
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, 
                    long arg3) {
            	//���item��ת������ҳ��
            	Intent intent = new Intent();  
            	intent.putExtra("survey", surveysEntryList.get(arg2));
                intent.setClass(SurveyList.this, QuestionList.class);
                //�����Զ���һ�������ƽ�����תЧ��
                startActivity(intent);
            }             
        });        
	}
	
	//�������ϻ�ȡʵʱ�ʾ�����
		private InputStream readSurveysDataFromInternet()  
	    {  
	        //�������ϻ�ȡʵʱ�ʾ�����  
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
		
		//�ӱ��ػ�ȡ�ʾ�����  
		private InputStream readSurveysDataFromFile()  
	    {  
	        //�ӱ��ػ�ȡ�ʾ�����  
	        InputStream inStream = null;  
	        try {  
	            inStream = this.getAssets().open("test.xml");  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        return inStream;  
	    }

}
