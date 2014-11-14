package net.sjava.book.ch09;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import net.sjava.book.R;
import net.sjava.book.ch08.LIFOQueueAsyncTask;
import net.sjava.book.ch08.PriorityThreadAAsyncTask;
import net.sjava.book.ch09.task.GetImageAsyncTask;
import net.sjava.book.ch09.task.GetImageLIFOAsyncTask;
import net.sjava.book.ch09.task.GetImagePriorityAsyncTask;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileArrayAdapter extends ArrayAdapter<FileInfo> {
	private LayoutInflater inflater = null;
	private FileViewHolder vHolder = null;
	private int type = 0;
	
	public FileArrayAdapter(Context ctx, int textViewResourceId, ArrayList<FileInfo> files) {
		super(ctx, textViewResourceId, files);
		this.inflater = LayoutInflater.from(ctx);
	}
	
	/**
	 * 
	 * 0 : AsyncTask 사용
	 * 1 : LIFOAsyncTask 사용
	 * 
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int getCount() {
		return super.getCount();
	}

	@Override
	public FileInfo getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		View v = inflater.inflate(R.layout.activity_ch09_item, null);
		
		// ViewHolder의 역활을 하지는 않는다. 
		vHolder = new FileViewHolder();			
		vHolder.iv_src = (ImageView) v.findViewById(R.id.activity_ch09_item_imageview);
		vHolder.txt_name = (TextView) v.findViewById(R.id.activity_ch09_item_filename);
		vHolder.txt_desc = (TextView) v.findViewById(R.id.activity_ch09_item_description);
			
		vHolder.txt_name.setText(getItem(position).filename);
		vHolder.txt_desc.setText(getItem(position).filesize +" / " + getItem(position).created);
		
		if(type == 0)
			new GetImageAsyncTask(vHolder.iv_src).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, getItem(position).thumbnail);
		
		if (type == 1)
			new GetImageLIFOAsyncTask(vHolder.iv_src).executeOnExecutor(LIFOQueueAsyncTask.THREAD_POOL_EXECUTOR, getItem(position).thumbnail);
		
		if(type == 2) {
			int value = position % 3;
			if( value == 0)
				new GetImagePriorityAsyncTask(Process.THREAD_PRIORITY_DEFAULT, vHolder.iv_src).executeOnExecutor(PriorityThreadAAsyncTask.THREAD_POOL_EXECUTOR, getItem(position).thumbnail);
			else
				new GetImagePriorityAsyncTask(Process.THREAD_PRIORITY_BACKGROUND, vHolder.iv_src).executeOnExecutor(PriorityThreadAAsyncTask.THREAD_POOL_EXECUTOR, getItem(position).thumbnail);
		}
			
		
		return v;
	}
	


	

	
	
	
	//new PriorityThreadAExampleTask(Process.THREAD_PRIORITY_DEFAULT).executeOnExecutor(PriorityThreadAExampleTask.THREAD_POOL_EXECUTOR, "");
}
