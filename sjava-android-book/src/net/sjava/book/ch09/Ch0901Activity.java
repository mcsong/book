package net.sjava.book.ch09;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import net.sjava.book.AbstractActivity;
import net.sjava.book.R;
import net.sjava.book.ch06.Ch06Activity;

public class Ch0901Activity extends AbstractActivity {
	
	private Button mBtn01;
	private Button mBtn02;
	private Button mBtn03;
	
	static final String listUrl = "http://www.sjava.net/book/list.json";
	public static List<FileInfo> fileInfos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_ch09_01);
		
		mBtn01 = (Button) findViewById(R.id.activity_ch09_01_btn_01);
		mBtn02 = (Button) findViewById(R.id.activity_ch09_01_btn_02);
		mBtn03 = (Button) findViewById(R.id.activity_ch09_01_btn_03);
		
		mBtn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {		        
				try {					
					new SetListView(Ch0901Activity.this, 0).execute(listUrl);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		mBtn02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {					
					new SetListView(Ch0901Activity.this, 1).execute(listUrl);
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		mBtn03.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {					
					new SetListView(Ch0901Activity.this, 2).execute(listUrl);
				} catch(Exception e) {
					e.printStackTrace();
				}	
			}
		});
	}
	
	
	class SetListView extends AsyncTask<String, Integer, String> {
		private Context ctx;
		private int type = 0; // 
		public SetListView(Context ctx, int type) {
			this.ctx = ctx;
			this.type = type;
		}
		
		private String loadList(Context ctx, String name) {
	        BufferedReader br = null;
	        try {
	            StringBuilder buf = new StringBuilder();
	            InputStream is = ctx.getAssets().open(name);
	            br = new BufferedReader(new InputStreamReader(is));

	            String str;
	            boolean isFirst = true;
	            while ( (str = br.readLine()) != null ) {
	                if (isFirst)
	                    isFirst = false;
	                else
	                    buf.append('\n');
	                buf.append(str);
	            }
	            
	            return buf.toString();
	        } catch (Exception e) {
	            Log.e(CNAME, "Error", e);
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (Exception e) {
	                    Log.e(CNAME, "Error", e);
	                }
	            }
	        }

	        return null;
	    }
		
		@Override
		protected String doInBackground(String... params) {
			try {
				
				return loadList(ctx, "list.json");
				//return HttpGet.Get(params[0]);
			} catch(Exception e) {
				Log.e("ERROR", e.getMessage());
			}
			
			return null;
		}
		
	   @Override
        protected void onPostExecute(String result) {
		   if(result == null)
			   return;
		   
		   Gson gson = new Gson();
		   FolderInfo folder = gson.fromJson(result, FolderInfo.class);			   
		   FragmentTransaction ft = getFragmentManager().beginTransaction();
	       ft.replace(R.id.activity_ch09_01_container_content, FileList.newInstance(Ch0901Activity.this, type, folder));
	       ft.commitAllowingStateLoss();
        }	
	}
	
	
	public static class FileList extends ListFragment {
		private Context ctx;
		private int type;
		private FolderInfo folder;
		private FileArrayAdapter fileAdapter;

		public static FileList newInstance(Context ctx, int type, FolderInfo folder) {
			FileList instance = new FileList();
			instance.ctx = ctx;
			instance.type = type;
			instance.folder = folder;
			if(folder != null)
				fileInfos = folder.files;
			
			return instance;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			this.fileAdapter = new FileArrayAdapter(ctx, R.layout.activity_ch09_item, (ArrayList<FileInfo>)folder.files);
			this.fileAdapter.setType(type);
			setListAdapter(fileAdapter);
		}
		
		@Override
		public void onListItemClick(ListView lv, View v, int position, long id) {			
			Intent intent = new Intent(ctx, Ch0901ViewerActivity.class);
			intent.putExtra("index", position);
			startActivity(intent);
		}
	}
}
