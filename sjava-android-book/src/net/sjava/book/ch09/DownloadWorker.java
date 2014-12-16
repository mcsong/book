package net.sjava.book.ch09;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

import android.util.Log;

public class DownloadWorker extends Thread {
	private CountDownLatch latch;
	private String name;
	private String url;
	private long start;
	private long end;
	
	public DownloadWorker(CountDownLatch latch, String name, String url, long start, long end) {
		this.latch = latch;
		this.name = name;
		this.url = url;
		this.start = start;
		this.end = end;
	}
	
	static int buffersize = 4096;
	public void download(String filename, String url, long s, long e) throws Exception {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			URL obj = new URL(url);
			URLConnection conn = obj.openConnection();
			
			String value = "bytes="+s +"-" +e;
			Log.d("DownloadWorker", "request range : " + value);
			
			conn.addRequestProperty("Range", value);
						
			in = new BufferedInputStream(conn.getInputStream());
			fout = new FileOutputStream(filename);

			byte data[] = new byte[buffersize];
			int count;
			while ((count = in.read(data, 0, buffersize)) != -1) {
				fout.write(data, 0, count);
			}
		} finally {
			if (in != null)
				in.close();
			
			if (fout != null)
				fout.close();
		}
	}
	
	public void run() {
		try {
			download(name, url, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (this.latch == null)
                return;
            
			latch.countDown();
		}
	}
}
