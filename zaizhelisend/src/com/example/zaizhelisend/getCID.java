package com.example.zaizhelisend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;

public class getCID extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle=intent.getExtras();
		Log.d("Getcid","onReceive() action="+bundle.getInt("action"));
		switch (bundle.getInt(PushConsts.CMD_ACTION)){
		case PushConsts.GET_MSG_DATA:
			byte[] payload=bundle.getByteArray("payload");
			if(payload!=null){
				String data=new String(payload);
				Log.d("Getcid","Got Payload:"+data);
				
				saveToDB.setnewINFO(data);
				
			}
			break;
		case PushConsts.GET_CLIENTID:
			String cid=bundle.getString("clientid");
			Log.d("Getcid","Got ClientID:"+cid);
			IndexActivity.clientid=cid;
			break;
		case PushConsts.THIRDPART_FEEDBACK:
			String appid = bundle.getString("appid");
			String taskid = bundle.getString("taskid");
			String actionid = bundle.getString("actionid");
			String result = bundle.getString("result");
			long timestamp = bundle.getLong("timestamp");

			Log.d("Getcid", "appid = " + appid);
			Log.d("Getcid", "taskid = " + taskid);
			Log.d("Getcid", "actionid = " + actionid);
			Log.d("Getcid", "result = " + result);
			Log.d("Getcid", "timestamp = " + timestamp);
			break;
		default:
			break;
		}
	}
}
