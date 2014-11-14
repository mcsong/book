package net.sjava.book.ch09;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class HttpGet {

	private static InputStream Request(String rUrl) throws IOException {
		URL url = new URL(rUrl);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(5 * 1000);

		return url.openStream();
	}

	public static InputStream GetStream(String rUrl) throws IOException {
		return Request(rUrl);
	}
	
	
	public static String Get(String rUrl) throws IOException {
		InputStream is = Request(rUrl);
		ByteArrayOutputStream buf = null;
		try {
			buf = new ByteArrayOutputStream();
			int result = -1;
			while ( (result = is.read()) != -1)
				buf.write(result);
	
			return buf.toString();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(is != null)
				is.close();
			
			if(buf != null)
				buf.close();
		}
		
		return null;
	}
}
