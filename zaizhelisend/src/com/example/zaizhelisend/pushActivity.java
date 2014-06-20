package com.example.zaizhelisend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class pushActivity extends Activity {
	private int count=-1;
	private Context mContext;
	private Button logoutbtn;
	static String clientid;
	static String email;
	static boolean logoutcheck;
	private String SERVICEURL;
	static SQLiteDatabase db;
	
	
	
	static String getEmail(){
		return email;
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        db = openOrCreateDatabase("Info.db",Context.MODE_PRIVATE,null);
		Bundle tbd=getIntent().getExtras();
		
		clientid=tbd.getString("inic");
		email=tbd.getString("usrname");
		
	       
		
		
		logoutbtn=(Button)findViewById(R.id.logoutbtn);
		logoutcheck=false;
		logoutbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				httpdisCon();
				if(logoutcheck==true){
					//back
					Intent intent = new Intent(pushActivity.this, IndexActivity.class);
					startActivity(intent);
	                pushActivity.this.finish();
				}
				else{
					
				}				
			}
			
		});
		
		//saveToDB();
		
		//dynamic Array
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		ListView listview = (ListView) findViewById(R.id.ListView01);
		ArrayList<String> arraylist = new ArrayList<String>();
		final ArrayList<String> titlelist = new ArrayList<String>();
		try{
			//SQLiteDatabase db = openOrCreateDatabase("Info.db",Context.MODE_PRIVATE, null);
			//db.execSQL("CREATE TABLE IF NOT EXISTS PUSHINFO(_id INTEGER PRIMARY KEY AUTOINCREMENT,usr VARCHAR,title VARCHAR,content VARCHAR)");
			db.execSQL("CREATE TABLE IF NOT EXISTS PUSHINFO(_id INTEGER PRIMARY KEY AUTOINCREMENT,usr VARCHAR,content VARCHAR)");
			email=email.replace("@",".");
			Log.d("EMAIL",email);
			Cursor cursor=db.rawQuery("SELECT * FROM PUSHINFO WHERE usr='a.abc.com'",null);		
			//"SELECT usr title content FROM PUSHINFO WHERE usr="+email
			count=cursor.getCount();
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				String tl=cursor.getString(2);
				Log.d("STR0",tl);
				titlelist.add(tl);
				String shows=tl+"\n";
				arraylist.add(shows);
				cursor.moveToNext();
			}
			cursor.close();
			Log.d("ARRAYSIZE",String.valueOf(arraylist.size()));
		}
			catch(Exception e){}
		for(int i=0;i<arraylist.size();i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("text",arraylist.get(i));
			listItem.add(map);
		}
		
		//display
		SimpleAdapter listItemAdapter=new SimpleAdapter(this,listItem,R.layout.listachv,
				new String[] {"text"},new int[] {R.id.Itxt});
		listview.setAdapter(listItemAdapter);
	}
	
	
	protected void httpdisCon(){
		new Thread(new Runnable(){
			@Override  
            public void run(){
				try{
	            	SERVICEURL="http://10.214.208.16/android/logout/"+clientid;
					URL url = new URL(SERVICEURL);
					HttpURLConnection urlConn=(HttpURLConnection) url.openConnection();
					urlConn.connect();
					Log.d("CIDBACK",String.valueOf(clientid));
					BufferedReader bufferReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
					String readLine = null;
					StringBuilder builder = new StringBuilder(); 
					while ((readLine = bufferReader.readLine()) != null) {
						builder.append(readLine);
					}
					bufferReader.close();
					urlConn.disconnect();	
					logoutcheck=true;
					db.close();
				}catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}).start();
	}
	@Override  
	public void onBackPressed() {  

	}  
	@Override
	protected void onDestroy(){
	    super.onDestroy();
	}
}
