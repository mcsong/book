package net.sjava.book;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;

public abstract class AbstractActivity extends Activity {
	protected String CNAME = this.getClass().getSimpleName();
	protected Context ctx() {
		return this;
	}
	
	static final boolean DEBUG = Boolean.TRUE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/* 
		if (DEBUG) {
	         StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	                 .detectAll()
	                 .penaltyLog()
	                 .penaltyDialog()
	                 .build());
	         
	         StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
	                 .detectAll()
	                 .penaltyLog()
	                 .build());
	     }
		 */
	     super.onCreate(savedInstanceState);
	}
}
