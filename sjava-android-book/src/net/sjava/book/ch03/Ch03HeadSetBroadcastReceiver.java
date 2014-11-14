package net.sjava.book.ch03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Ch03HeadSetBroadcastReceiver extends BroadcastReceiver {
	static final String TAG = Ch03HeadSetBroadcastReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context context, Intent intent) {		
		if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
			int state = intent.getIntExtra("state", -1);
			
			if(state == 0)
				Log.i(TAG, "헤드셋 연결 해제");
			
			if(state == 1)
				Log.i(TAG, "헤드셋 연결");
		}
	}
}