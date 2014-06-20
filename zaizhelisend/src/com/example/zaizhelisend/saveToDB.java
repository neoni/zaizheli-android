package com.example.zaizhelisend;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class saveToDB {
	private static String contents;
	private static String email;

     static void saveToDB(){
			String getcontent = null;
			getcontent=contents;
			Log.d("CONTENTS",contents);
			pushActivity.db.execSQL("CREATE TABLE IF NOT EXISTS PUSHINFO(_id INTEGER PRIMARY KEY AUTOINCREMENT,usr VARCHAR,content VARCHAR)");
			ContentValues values =new ContentValues();
			email=pushActivity.getEmail();
			email=email.replace("@",".");
			values.put("usr", email);
			values.put("content",getcontent);
			pushActivity.db.insert("PUSHINFO","_id", values);
        }
        static void setnewINFO(String data){
				contents = data;
				saveToDB();
        }
	
}
