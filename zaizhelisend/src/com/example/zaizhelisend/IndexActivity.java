package com.example.zaizhelisend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import com.igexin.sdk.PushManager;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


@SuppressLint("NewApi")
public class IndexActivity extends Activity {
	private GestureDetector  myges;
	private EditText txusr,txpasswd;
	private Button lgbutton;
	private String email,passwd;
	static boolean logincheck;
	private boolean errorcheck;
	static Context mContext;
	static String clientid;
	public static String SERVICEURL="http://10.214.208.16/android/verify/";
	public static String SERVICEURL1="http://10.214.208.16/android/logout/";
	public static final int CONNECTION_TIMEOUT_INT = 8000;
	public static final int READ_TIMEOUT_INT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        PushManager.getInstance().initialize(this.getApplicationContext());
        myges=new GestureDetector(this, new MyGestureListener());
        txusr=(EditText)findViewById(R.id.loginusrname);
        txpasswd=(EditText)findViewById(R.id.loginpasswd);
        lgbutton=(Button)findViewById(R.id.loginbutton);
        mContext = IndexActivity.this;
        logincheck=false;
        errorcheck=false;
        lgbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				email=txusr.getText().toString();
				passwd=txpasswd.getText().toString();
				if(passwd.isEmpty()) {
					Toast.makeText(mContext,"Your password is Empty!",Toast.LENGTH_LONG).show();
					logincheck=false;
					errorcheck=true;
				}
				if(email.isEmpty()) {
					Toast.makeText(mContext,"Your email is Empty!",Toast.LENGTH_LONG).show();
					logincheck=false;
					errorcheck=true;
				}
				if(passwd.length()<6) {
					Toast.makeText(mContext,"Your password is illegal!",Toast.LENGTH_LONG).show();
					logincheck=false;
					errorcheck=true;
				}
				if (!errorcheck) {
					httpCon();
					if(logincheck==true){
						Intent intent = new Intent(IndexActivity.this, pushActivity.class);
						Bundle tbd=new Bundle();
						tbd.putString("inic",clientid);
						tbd.putString("usrname", email);
		                intent.putExtras(tbd);
						startActivity(intent);
		                IndexActivity.this.finish();
					}
					else{
					//no jump wait for next input;
					}
				}
			}
		});
    }
    
    
    
    protected void httpCon() {  
    	  
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
            	try{
	            	SERVICEURL="http://10.214.208.16/android/verify/"+email+"/"+passwd+"/"+clientid;
					URL url = new URL(SERVICEURL);
					HttpURLConnection urlConn=(HttpURLConnection) url.openConnection();
					urlConn.connect();
					BufferedReader bufferReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
					String readLine = null;
					StringBuilder builder = new StringBuilder(); 
					while ((readLine = bufferReader.readLine()) != null) {
						builder.append(readLine);
					}
					bufferReader.close();
					urlConn.disconnect();	
					String re = builder.toString();
					int pos = re.indexOf("resultCode");
					int poss = re.indexOf("\"",pos+13);
					String code = re.substring(pos+13,poss);
					if(code.equals("INVALID")){
						pos = re.indexOf("exceptionMsg");
						poss = re.indexOf("\"", pos+15);
						String res = re.substring(pos+15, poss-1);
						//Toast.makeText(IndexActivity.mContext,res,Toast.LENGTH_LONG).show();
						IndexActivity.logincheck=false;
					}
					else if(code.equals("SUCCESS")){
						IndexActivity.logincheck=true;
					}
				}catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
               
            }  
        }).start();  
  
    }  

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }
    public class MyGestureListener implements OnGestureListener {

		@Override
		public boolean onDown(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return false;
		}
    
    }
	@Override  
	public void onBackPressed() {  
	        finish();  
	}  
	@Override
	protected void onDestroy(){
	    super.onDestroy();
	}
    
}
