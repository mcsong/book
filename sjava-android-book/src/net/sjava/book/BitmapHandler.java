package net.sjava.book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class BitmapHandler {
	static final String PRE_PATH = Environment.getExternalStorageDirectory() +"/net.sjava.book";
	
	public static BitmapHandler newInstance() {
		return new BitmapHandler();	
	}
	
	private BitmapHandler() { }
	
	public Bitmap load(String fileName) throws Exception {
		return BitmapFactory.decodeFile(PRE_PATH +"/" + fileName);
	}
	
	public boolean save(Bitmap bitmap, String fileName) throws Exception {
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(PRE_PATH +"/" + fileName));
			return bitmap.compress(Bitmap.CompressFormat.PNG,  95,  fos);
		} finally {
			if(fos != null)
				fos.close();
		}

	}
	
}
