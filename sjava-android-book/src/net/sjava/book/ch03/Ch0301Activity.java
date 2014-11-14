package net.sjava.book.ch03;

import net.sjava.book.AbstractActivity;
import net.sjava.book.R;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Ch0301Activity extends AbstractActivity {
	private TextView txtView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch03_01);

		txtView = (TextView)findViewById(R.id.activity_ch03_01_txtView_01);		
		// 루트뷰 확인을 위한 부분
		View v = txtView.getRootView();
	}
}
