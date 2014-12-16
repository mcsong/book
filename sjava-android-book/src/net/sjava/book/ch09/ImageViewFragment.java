package net.sjava.book.ch09;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import net.sjava.book.R;
import net.sjava.book.ch09.task.GetImageAsyncTask;


public class ImageViewFragment extends Fragment {
	private FileInfo fileInfo = null;

	static ImageViewFragment newInstance(FileInfo fileInfo) {
		ImageViewFragment instance = new ImageViewFragment();
		instance.fileInfo = fileInfo;
		return instance;
	}

	private ImageView iv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_ch09_01_viewer_content,
				container, false);
		iv = (ImageView) v.findViewById(R.id.activity_ch09_01_viewer_content_imageview);

		new GetImageAsyncTask(iv).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, fileInfo.thumbnail);
		
		return v;
	}
}