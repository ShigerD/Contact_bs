package com.example.phonecatch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

public class PhoneCallObserver extends ContentObserver{

	private Context context;
	private final String TAG="PhoneCallObserver";
	
	public PhoneCallObserver(Handler handler,Context context) {
		super(handler);
		this.context=context;	
	}

	@Override
	public void onChange(boolean selfChange) {
		Log.i(TAG, "onChange---->");
	    
	}
	
}
	
