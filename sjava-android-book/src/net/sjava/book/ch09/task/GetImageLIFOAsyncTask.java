package net.sjava.book.ch09.task;

import java.io.IOException;
import java.io.InputStream;

import net.sjava.book.ch08.LIFOQueueAsyncTask;
import net.sjava.book.ch09.HttpGet;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class GetImageLIFOAsyncTask extends LIFOQueueAsyncTask<String, Void, Bitmap> {
	private ImageView iv;
	public GetImageLIFOAsyncTask(ImageView iv) {
		this.iv = iv;
	}
	
	@Override
	protected Bitmap doInBackground(String... args) {
		InputStream is = null;
		
		try {
			is = HttpGet.GetStream(args[0]);
			return BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return null;
	}
	
	@Override
	public void onPostExecute(Bitmap bm) {
		if(bm == null)
			return;
		
		iv.setImageBitmap(bm);
	}
}	