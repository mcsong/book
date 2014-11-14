package net.sjava.book.ch06;

import java.util.ArrayList;
import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

public class Ch0606AsyncLoader extends AsyncTaskLoader<List<String>> {
	private static String TAG = Ch0606AsyncLoader.class.getSimpleName();

	public static Ch0606AsyncLoader newInstance(Context ctx) {
		return Ch0606AsyncLoader.newInstance(ctx, 1);
	}
	
	public static Ch0606AsyncLoader newInstance(Context ctx, int type) {
		Ch0606AsyncLoader instance = new Ch0606AsyncLoader(ctx);
		instance.type = type;
		return instance;
	}
	
	private List<String> result;
	private int type = 1;
	
	private Ch0606AsyncLoader(Context ctx) {
		super(ctx);
	}

	@Override
	public void deliverResult(List<String> strArray) {
		if (isReset()) {
			if (this.result != null)
				this.result = null;

			return;
		}

		this.result = strArray;

		if (isStarted())
			super.deliverResult(strArray);
	}

	@Override
	protected void onStartLoading() {
		Log.d(TAG, "onStartLoading");

		if (this.result != null)
			deliverResult(this.result);

		if (takeContentChanged() || this.result == null)
			forceLoad();
	}

	@Override
	public List<String> loadInBackground() {
		result = new ArrayList<String>();
		
		if(type == 2) {
			result.add("button click 1 번");
			result.add("button click 2 번");
		} else {
			result.add("1 번");
			result.add("2 번");
		}
		
		
		return result;
	}

	@Override
	protected void onStopLoading() {
		super.onStopLoading();
		cancelLoad();
	}

	@Override
	protected void onReset() {
		super.onReset();
		onStopLoading();
		
		this.result = null;
	}
}
