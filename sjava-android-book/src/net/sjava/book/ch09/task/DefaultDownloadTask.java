package net.sjava.book.ch09.task;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class DefaultDownloadTask extends AsyncTask<String, Void, String> {
	static final String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
	
	private long startTime = 0L;
	private long endTime = 0L;
	
	private String url;
	private String fullFilename;
	private Button mButton;
	private TextView mTextView;
	
	public DefaultDownloadTask(String url, Button btn, TextView tv) {
		this.url = url;
		this.fullFilename = directory +"/" +String.valueOf(System.currentTimeMillis());
		this.mButton = btn;
		this.mTextView = tv;
	}

	@Override
	protected void onPreExecute() {
		mButton.setEnabled(false);
		startTime = System.currentTimeMillis();
	}
	
	@Override
	protected String doInBackground(String... params) {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		int buffersize = 2048;
		
		try {
			in = new BufferedInputStream(new URL(url).openStream());
			fout = new FileOutputStream(fullFilename);

			byte data[] = new byte[buffersize];
			int count;
			while ((count = in.read(data, 0, buffersize)) != -1) {
				fout.write(data, 0, count);
			}
		} catch(Exception e) {
			Log.e("DefaultDownloadTask", "error", e);
		} finally {
			try {
			if (in != null)
				in.close();
			
			if (fout != null)
				fout.close();
			} catch(IOException io){}
		}
		
		return "";
	}
	
	@Override
	protected void onPostExecute(String result) {
		mButton.setEnabled(true);
		
		endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		mTextView.setText("실행 시간 : " + elapsedTime );
		Log.d("DefaultDownloadTask", " 실행 시간 : "+ (endTime - startTime) );
	}
}