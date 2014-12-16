package net.sjava.book.ch09;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import net.sjava.book.AbstractActivity;
import net.sjava.book.R;
import net.sjava.book.ch09.task.DefaultDownloadTask;

public class Ch0902Activity extends AbstractActivity {
	
	private TextView mTextView;
	
	private Button mBtn01;
	private Button mBtn02;
	private Button mBtn03;
	private Button mBtn04;
	
	static final String DATA_URL = "http://sjava.net/wp-content/uploads/2014/12/Android_-And_You.mp4";
	
	public static String RESUME_FILE_NAME = "resume.mp4";
	public static String PARTIAL_FILE_NAME = "partial.mp4";
	
	private ResumeDownloadTask resumeTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch09_02);
		
		mTextView = (TextView) findViewById(R.id.activity_ch09_02_txtview);
		mBtn01 = (Button) findViewById(R.id.activity_ch09_02_btn_01);
		mBtn02 = (Button) findViewById(R.id.activity_ch09_02_btn_02);
		mBtn03 = (Button) findViewById(R.id.activity_ch09_02_btn_03);
		mBtn04 = (Button) findViewById(R.id.activity_ch09_02_btn_04);
		
		mBtn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {		        
				new DefaultDownloadTask(DATA_URL, mBtn01, mTextView).execute("");
			}
		});
		
		
		// 이어받기
		mBtn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resumeTask = new ResumeDownloadTask(DATA_URL, mBtn02, mTextView);
				resumeTask.execute("");
			}
		});
		
		// 종료
		mBtn03.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				resumeTask.cancel(true);
			}
		});
		
		mBtn04.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new PartialDownloadTask(DATA_URL, mBtn04, mTextView).execute("");
			}
		});
	}	
	
	
	static class PartialDownloadTask extends AsyncTask<String, Void, String> {
		static final String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
		
		private long startTime = 0L;
		private long endTime = 0L;
		
		private Button mButton;
		private TextView mTextView;
		
		public PartialDownloadTask(String url, Button btn, TextView tv) {
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
			long originSize = getContentSize(DATA_URL);
			
			// 이 경우에는 분할 수신기능을 사용할 수 없다.
			if(originSize == 0) {
				return "";
			}
			
			long pp = originSize / max;	
			// CountDownLatch를 사용해서 작업이 다 완료하기를 기다린다.
	    	final CountDownLatch latch = new CountDownLatch(max);
	        
	        for (long i = 0; i < max; i++) {
	        	if(i ==0)        	
	        		new DownloadWorker(latch, directory +"/" + String.valueOf(i), DATA_URL, 0, pp).start();
	        	else if (i == max -1) {
	        		long s = i * pp + 1;
	        		//long e = (i+1) * pp;
	        		new DownloadWorker(latch, directory +"/" +String.valueOf(i), DATA_URL, s, originSize).start();
	        	} else {
	        		long s = i * pp + 1;
	        		long e = (i+1) * pp;
	        		
	        		new DownloadWorker(latch, directory +"/" +String.valueOf(i), DATA_URL, s, e).start();
	        	}
	        }
	        
	        try {
	        	latch.await();
	        	merge();
	        } catch(Exception e) {
	        
	        }
	        return "";
		}
			

		@Override
		protected void onPostExecute(String result) {
			mButton.setEnabled(true);
			
			endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			mTextView.setText("실행 시간 : " + elapsedTime );
			Log.d("PartialDownloadTask", " 실행 시간 : "+ (endTime - startTime) );
		}
		
    	static int max = 3;
		static int bufferSize = 4096;
		
		// 다운로드 받은 파일을 머지한다.
		static void merge() {
	        File ofile = new File(directory +"/"+ "latch.mp4");
			FileOutputStream fos;
			FileInputStream fis;
			
			List<File> list = new ArrayList<File>();
			for(int i=0; i < max; i++) {
				list.add(new File(directory +"/" +String.valueOf(i)));
			}
			
			try {
			    fos = new FileOutputStream(ofile, true);
			    for (File file : list) {
			        fis = new FileInputStream(file);
			        
					byte data[] = new byte[bufferSize];
					int count;
					
			        while ((count = fis.read(data, 0, bufferSize)) != -1) {
						fos.write(data, 0, count);
					}
			       
			        fos.flush();
			        fis.close();
			        fis = null;
			    }
			    
			    for (File file : list) {
			    	file.delete();
			    }
			    
			    fos.close();
			    fos = null;
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	static class ResumeDownloadTask extends AsyncTask<String, Void, String> {
		static final String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
		
		private long startTime = 0L;
		private long endTime = 0L;
		
		private String url;
		private String fullFilename;
		private Button mButton;
		private TextView mTextView;
		
		public ResumeDownloadTask(String url, Button btn, TextView tv) {
			this.url = url;
			this.fullFilename = directory +"/" + RESUME_FILE_NAME;
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
			RandomAccessFile rFile = null;
			
			int count;			
			int buffersize = 2048;
			byte data[] = new byte[buffersize];
			
			try {
				long originSize = getContentSize(DATA_URL);
				
				// 0보다 큰 경우에만 이어받기를 지원한다.
				File file = new File(fullFilename);
				
				if(originSize > 0 && file.exists()) {
					long fSize = file.length();
					
					Log.d("ResumeDownloadTask", "origin size : " + originSize +", local file size : " + fSize);
					
					if(originSize == fSize)
						return "이미 다 수신함";
										
					URL url = new URL(DATA_URL);
					URLConnection conn = url.openConnection();
					
					String value = "bytes="+ fSize + "-";
					System.out.println("r -->" + value);
					
					conn.addRequestProperty("Range", value);
								
					in = new BufferedInputStream(conn.getInputStream());
					rFile = new RandomAccessFile(file.getAbsolutePath(), "rw");  
					rFile.seek(fSize);
					
					while ((count = in.read(data, 0, buffersize)) != -1) {
						rFile.write(data, 0, count); 
						if(isCancelled())
							break;
				    } 
				} else {
				
					in = new BufferedInputStream(new URL(url).openStream());
					fout = new FileOutputStream(fullFilename);
	
					while ((count = in.read(data, 0, buffersize)) != -1) {
						fout.write(data, 0, count);
						
						if(isCancelled())
							break;
					}
				}
			} catch(Exception e) {
				Log.e("DefaultDownloadTask", "error", e);
			} finally {
				try {
					if (in != null)
						in.close();
					
					if (fout != null)
						fout.close();
					
					if (rFile != null)
						rFile.close();
				} catch(IOException io){}
			}
			
			return "";
		}
		
		@Override
		protected void onCancelled(String result) {
			mButton.setEnabled(true);
			endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			mTextView.setText("실행 시간 : " + elapsedTime );
		}
		
		@Override
		protected void onPostExecute(String result) {
			mButton.setEnabled(true);
			
			endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			mTextView.setText("실행 시간 : " + elapsedTime );
			Log.d("ResumeDownloadTask", " 실행 시간 : "+ (endTime - startTime) );
		}
	}
	
	
	/**
	 * 요청한 주소에 해당하는 리소스의 크기를 알려준다.
	 * 0이면 이어받기나 분할 수신기능을 제공하지 않는다.
	 * 
	 * @param urlString
	 * @return
	 */
	public static long getContentSize(String urlString) {
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			conn.addRequestProperty("Range", "bytes=0-1");
			
			String rangeAccept = conn.getHeaderField("Accept-Ranges");
			if(!"bytes".equals(rangeAccept))
				return 0L;
			
			String contentSize = conn.getHeaderField("Content-Range");
			return Long.parseLong(contentSize.split("/")[1]);
		} catch (Exception e) {
			Log.e(Ch0902Activity.class.getSimpleName(), "error", e);
		}
		
		return 0L;
	}
	
	
	
	
	
}
