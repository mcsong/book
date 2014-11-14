package net.sjava.book;

import android.app.Application;
import android.os.StrictMode;

public class BookApplication extends Application {
	
	public static final String[] strArray ={"android_01.png","android_02.png","android_03.png","android_04.png","android_05.png","android_06.png"}; 
	public static final Integer[] imageArray = {R.drawable.android_01, R.drawable.android_02, R.drawable.android_03, R.drawable.android_04, R.drawable.android_05, R.drawable.android_06}; 
	
	public static boolean DEVELOPER_MODE = true;
	
	@Override 
	public void onCreate() {

		if (DEVELOPER_MODE) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads()
					.detectDiskWrites()
					.detectNetwork()
					.penaltyLog()
					.build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectActivityLeaks()
					.detectLeakedSqlLiteObjects()
					.detectLeakedClosableObjects()
					.penaltyLog()
					.penaltyDeath()
					.build());
		}
		super.onCreate();

	}
}
