package com.lknhac.nlawyer;

import com.lknhac.nlawyer.util.Const;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends DrawerLayoutActivity {
	 private ListView mTitle;
	 private String[] TITLES ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		init();
	}

	private void init(){
		TITLES = new String[]{getString(R.string.t_1),   
		    	getString(R.string.t_2),
		    	getString(R.string.t_3),
		    	getString(R.string.t_4),
		    	getString(R.string.t_5),
		    	getString(R.string.t_6),
		    	getString(R.string.t_7),
		    	getString(R.string.t_8),
		    	getString(R.string.t_9),
		    	getString(R.string.t_10),
		    	getString(R.string.t_11),
		    	getString(R.string.t_12),
		    	getString(R.string.t_13),
		    	getString(R.string.t_14),
		    	getString(R.string.t_15),
		    	getString(R.string.t_16)};
		mTitle = (ListView) findViewById(R.id.lv_title);
		mTitle.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
	                TITLES));
		mTitle.setOnItemClickListener(new TitleItemClickListener());
	}
	 private class TitleItemClickListener implements ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	          Toast.makeText(getApplicationContext(), TITLES[position], Toast.LENGTH_SHORT).show();
	          String strContents = "t_"+String.valueOf(position+1)+".html";
	          Bundle bundle = new Bundle();
				bundle.putString(Const.CONTENTS,
						strContents);
				// After all data has been entered and calculated, go to new
				// page for results
				Intent myIntent = new Intent();
				myIntent.putExtras(bundle);
				myIntent.setClass(getBaseContext(), ContentActivity.class);
				startActivity(myIntent);
	        }
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void setupView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main);
		
	}
}
