package net.sjava.book.ch06;


import java.util.List;

import net.sjava.book.AbstractActivity;
import net.sjava.book.R;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Ch0606Activity extends AbstractActivity implements LoaderManager.LoaderCallbacks<List<String>> {
	
	private ListView mListView;
	private Ch0606Adapter mAdapter;
	private Button mBtn01;
	
	private Loader<List<String>> loader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch06_06);
		
		mListView = (ListView) findViewById(R.id.activity_ch06_05_listview);
		mBtn01 = (Button)findViewById(R.id.activity_ch06_05_btn_01);
		mBtn01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle b = new Bundle();
				b.putInt("type", 2);
				getLoaderManager().restartLoader(0, b, Ch0606Activity.this);
			}
		});
		
		Bundle b = new Bundle();
		b.putInt("type", 1);
		
		loader = getLoaderManager().initLoader(0,  b, this);

	}

	@Override
	public Loader<List<String>> onCreateLoader(int id, Bundle bundle) {
		Log.d(CNAME, "onCreateLoader");
		
		// 로더를 만드는 메서드
		if(bundle == null)
			return Ch0606AsyncLoader.newInstance(ctx());
		
		int type = bundle.getInt("type", 1);
		
		return Ch0606AsyncLoader.newInstance(ctx(), type);
	}

	@Override
	public void onLoadFinished(Loader<List<String>> loader, List<String> strArray) {
		Log.d(CNAME, "onLoadFinished");
		
		// 비동기 처리가 완료되면 불려지는 콜백 메서드..
		mAdapter = new Ch0606Adapter(ctx(), R.id.activity_ch06_05_item_textview, strArray);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onLoaderReset(Loader<List<String>> loader) {
		Log.d(CNAME, "onLoaderReset");
		
		// 데이터를 제거한다.
		mAdapter.clear();
		mAdapter.notifyDataSetChanged();
	}

}
