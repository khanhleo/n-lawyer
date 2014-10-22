package com.lknhac.nlawyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.lknhac.nlawyer.adapter.ExpandableListAdapter;
import com.lknhac.nlawyer.util.Const;

public class MainActivity extends DrawerLayoutActivity {

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		//setContentView(R.layout.activity_main);
//		init();
//	}

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_list_class);

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				// Toast.makeText(getApplicationContext(),
				// listDataHeader.get(groupPosition) + " Expanded",
				// Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				// Toast.makeText(getApplicationContext(),
				// listDataHeader.get(groupPosition) + " Collapsed",
				// Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				//String mSection= String.valueOf(groupPosition + 1);
				String mTitle = "t"+ String.valueOf(groupPosition + 1) + "_"
						+ String.valueOf(childPosition+1)+".htm";
				
				Bundle bundle = new Bundle();
				bundle.putString(Const.TITLE,
						mTitle);
				// After all data has been entered and calculated, go to new
				// page for results
				Intent myIntent = new Intent();
				myIntent.putExtras(bundle);
				myIntent.setClass(getBaseContext(), ContentActivity.class);
				startActivity(myIntent);
				
				Toast.makeText(getApplicationContext(), mTitle, Toast.LENGTH_SHORT).show();
				// Add the bundle into myIntent for referencing variables
				
				return false;
			}
		});
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding header data
		listDataHeader.add(getString(R.string.sec_1));
		listDataHeader.add(getString(R.string.sec_2));
		listDataHeader.add(getString(R.string.sec_3));
		listDataHeader.add(getString(R.string.sec_4));
		listDataHeader.add(getString(R.string.sec_5));
		listDataHeader.add(getString(R.string.sec_6));
		listDataHeader.add(getString(R.string.sec_7));
		

		// Adding child data
		List<String> Sec1 = new ArrayList<String>();
		Sec1.add(getString(R.string.t1_1));
		Sec1.add(getString(R.string.t1_2));
		Sec1.add(getString(R.string.t1_3));
		Sec1.add(getString(R.string.t1_5));
		Sec1.add(getString(R.string.t1_6));
		Sec1.add(getString(R.string.t1_8));
		
		List<String> Sec2 = new ArrayList<String>();
		Sec2.add(getString(R.string.t2_10));
		Sec2.add(getString(R.string.t2_11));
		Sec2.add(getString(R.string.t2_12));
		Sec2.add(getString(R.string.t2_13));
		Sec2.add(getString(R.string.t2_14));
		Sec2.add(getString(R.string.t2_15));
		Sec2.add(getString(R.string.t2_16));
		
		List<String> Sec3 = new ArrayList<String>();
		Sec3.add(getString(R.string.t3_17));
		Sec3.add(getString(R.string.t3_18));
		Sec3.add(getString(R.string.t3_19));
		Sec3.add(getString(R.string.t3_20));
		Sec3.add(getString(R.string.t3_21));
		
		List<String> Sec4 = new ArrayList<String>();
		Sec4.add(getString(R.string.t4_22));
		Sec4.add(getString(R.string.t4_23));
		Sec4.add(getString(R.string.t4_24));
		Sec4.add(getString(R.string.t4_25));
		
		List<String> Sec5 = new ArrayList<String>();
		Sec5.add(getString(R.string.t5_26));
		Sec5.add(getString(R.string.t5_27));
		Sec5.add(getString(R.string.t5_28));
		Sec5.add(getString(R.string.t5_29));
		Sec5.add(getString(R.string.t5_30));
		Sec5.add(getString(R.string.t5_31));
		Sec5.add(getString(R.string.t5_32));
		Sec5.add(getString(R.string.t5_33));

		List<String> Sec6 = new ArrayList<String>();
		Sec6.add(getString(R.string.t6_34));
		Sec6.add(getString(R.string.t6_35));
		Sec6.add(getString(R.string.t6_36));
		
		List<String> Sec7 = new ArrayList<String>();
		Sec7.add(getString(R.string.t7_37));


		listDataChild.put(listDataHeader.get(0), Sec1);
		listDataChild.put(listDataHeader.get(1), Sec2); // Header, Child data
		listDataChild.put(listDataHeader.get(2), Sec3);
		listDataChild.put(listDataHeader.get(3), Sec4);
		listDataChild.put(listDataHeader.get(4), Sec5);
		listDataChild.put(listDataHeader.get(5), Sec6);
		listDataChild.put(listDataHeader.get(6), Sec7);
	}
	@Override
	protected void setupView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_list_class);
		mStrTitle = (String) getTitle();
	}
}
