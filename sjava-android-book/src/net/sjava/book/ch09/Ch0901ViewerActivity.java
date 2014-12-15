package net.sjava.book.ch09;

import java.util.ArrayList;
import java.util.List;

import net.sjava.book.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

public class Ch0901ViewerActivity extends FragmentActivity {

	private ViewPageAdapter mPageAdapter;
	private TextView mTextView;
	private ViewPager mViewPager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch09_01_viewer);

		int pos = getIntent().getIntExtra("index", 0);
		
		mPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), getImageViewFragments());

		mTextView = (TextView) findViewById(R.id.activity_ch09_01_viewer_txtview);
		mViewPager = (ViewPager) findViewById(R.id.activity_ch09_01_viewer_pager);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				//activity_ch09_viewer_txtview
				mTextView.setText(position +"");
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		mViewPager.setAdapter(mPageAdapter);
		mViewPager.setCurrentItem(pos);
		mTextView.setText(pos +"");
	}

	private List<Fragment> getImageViewFragments() {
		List<Fragment> fragments = new ArrayList<Fragment>();
		
		List<FileInfo> files = Ch0901Activity.fileInfos;
		if(files == null)
			return fragments;
		
		for(int i=0; i<files.size(); i++)
			fragments.add(ImageViewFragment.newInstance(files.get(i)));
		
		return fragments;
	}

	class ViewPageAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragments;

		public ViewPageAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
	}
}
