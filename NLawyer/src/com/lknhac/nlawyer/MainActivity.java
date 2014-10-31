package com.lknhac.nlawyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.bool;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.lknhac.nlawyer.adapter.ExpandableListAdapter;
import com.lknhac.nlawyer.rate.RateMeMaybe;
import com.lknhac.nlawyer.rate.RateMeMaybe.OnRMMUserChoiceListener;
import com.lknhac.nlawyer.util.Const;

public class MainActivity extends DrawerLayoutActivity implements OnRMMUserChoiceListener {

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// //setContentView(R.layout.activity_main);
	// init();
	// }
	/** The view to show the ad. */
	private AdView adView;

	/* Your ad unit id. Replace with your actual ad unit id. */
//	private static final String AD_UNIT_ID = "ca-app-pub-5056196661864424/7874061193";
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	List<String> listDataHeader2;
	HashMap<String, List<String>> listDataChild;
	HashMap<String, List<String>> listDataChildChapTag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_list_class);


		
		// check connection
		if (Const.isNetworkAvailable(getApplicationContext())) {
			
			// rate app
			SharedPreferences prefs = getSharedPreferences(Const.NAME, MODE_PRIVATE); 
			boolean restoredText = prefs.getBoolean(Const.DONT_SHOW_AGAIN, false);
			if (!restoredText) {
				rateApp();
			}
			
			
			// Create an ad.
			adView = new AdView(this);
			adView.setAdSize(AdSize.BANNER);
			adView.setAdUnitId(Const.AD_UNIT_ID);

			// Add the AdView to the view hierarchy. The view will have no size
			// until the ad is loaded.
			LinearLayout layout = (LinearLayout) findViewById(R.id.llAdmobs);
			layout.addView(adView);

			// Create an ad request. Check logcat output for the hashed device
			// ID to
			// get test ads on a physical device.
			AdRequest adRequest = new AdRequest.Builder().build();

			// Start loading the ad in the background.
			adView.loadAd(adRequest);
		}else{
			LinearLayout layout = (LinearLayout) findViewById(R.id.llBottom);
			layout.setVisibility(View.GONE);
		}

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild, listDataChildChapTag);

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
				if (groupPosition == 6) {
					String mTitle = "t" + String.valueOf(groupPosition + 1)
							+ "_1.htm";
					String mContent = getString(R.string.sec) + "7";
					// String mContents = listDataChild.get(key)
					Bundle bundle = new Bundle();
					bundle.putString(Const.TITLE, mTitle);
					bundle.putString(Const.CONTENTS, mContent);
					bundle.putInt(Const.SEC_NUM, groupPosition + 1);
					// bundle.putString(Const.CONTENTS, )
					// After all data has been entered and calculated, go to new
					// page for results
					Intent myIntent = new Intent();
					myIntent.putExtras(bundle);
					myIntent.setClass(getBaseContext(), ContentActivity.class);
					startActivity(myIntent);
				}
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
				// ImageView img = (ImageView) findViewById(R.id.imgArrow);
				// img.setBackgroundResource(R.drawable.ico_up);
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
				// String mSection= String.valueOf(groupPosition + 1);
				String mTitle = "t" + String.valueOf(groupPosition + 1) + "_"
						+ String.valueOf(childPosition + 1) + ".htm";
				String mContent = getString(R.string.chap)
						+ listDataChildChapTag.get(
								listDataHeader.get(groupPosition)).get(
								childPosition);
				// String mContents = listDataChild.get(key)
				Bundle bundle = new Bundle();
				bundle.putString(Const.TITLE, mTitle);
				bundle.putString(Const.CONTENTS, mContent);
				bundle.putInt(Const.SEC_NUM, groupPosition + 1);
				// bundle.putString(Const.CONTENTS, )
				// After all data has been entered and calculated, go to new
				// page for results
				Intent myIntent = new Intent();
				myIntent.putExtras(bundle);
				myIntent.setClass(getBaseContext(), ContentActivity.class);
				startActivity(myIntent);

//				Toast.makeText(getApplicationContext(), mTitle,
//						Toast.LENGTH_SHORT).show();
				// Add the bundle into myIntent for referencing variables

				return false;
			}
		});
	}

	private void rateApp(){
		RateMeMaybe.resetData(this);
		RateMeMaybe rmm = new RateMeMaybe(this);
		rmm.setPromptMinimums(0, 0, 0, 0);
		rmm.setRunWithoutPlayStore(true);
		rmm.setAdditionalListener(this);
		rmm.setDialogMessage(getString(R.string.rate));
		rmm.setDialogTitle(getString(R.string.title_rate_dialog));
		rmm.setPositiveBtn(getString(R.string.positive_button));
		rmm.setNegativeBtn(getString(R.string.negative_button));
		rmm.setNeutralBtn(getString(R.string.neutral_button));
		rmm.run();
	}
	@Override
	public void onResume() {
		super.onResume();
		if (adView != null) {
			adView.resume();
		}
	}

	@Override
	public void onPause() {
		if (adView != null) {
			adView.pause();
		}
		super.onPause();
	}

	/** Called before the activity is destroyed. */
	@Override
	public void onDestroy() {
		// Destroy the AdView.
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		listDataChildChapTag = new HashMap<String, List<String>>();

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
		Sec1.add(getString(R.string.t1_9));

		List<String> Sec2 = new ArrayList<String>();
		Sec2.add(getString(R.string.t2_1));
		Sec2.add(getString(R.string.t2_2));
		Sec2.add(getString(R.string.t2_3));
		Sec2.add(getString(R.string.t2_4));
		Sec2.add(getString(R.string.t2_5));
		Sec2.add(getString(R.string.t2_6));
		Sec2.add(getString(R.string.t2_7));

		List<String> Sec3 = new ArrayList<String>();
		Sec3.add(getString(R.string.t3_1));
		Sec3.add(getString(R.string.t3_2));
		Sec3.add(getString(R.string.t3_3));
		Sec3.add(getString(R.string.t3_4));
		Sec3.add(getString(R.string.t3_5));

		List<String> Sec4 = new ArrayList<String>();
		Sec4.add(getString(R.string.t4_1));
		Sec4.add(getString(R.string.t4_2));
		Sec4.add(getString(R.string.t4_3));
		Sec4.add(getString(R.string.t4_4));

		List<String> Sec5 = new ArrayList<String>();
		Sec5.add(getString(R.string.t5_1));
		Sec5.add(getString(R.string.t5_2));
		Sec5.add(getString(R.string.t5_3));
		Sec5.add(getString(R.string.t5_4));
		Sec5.add(getString(R.string.t5_5));
		Sec5.add(getString(R.string.t5_6));
		Sec5.add(getString(R.string.t5_7));
		Sec5.add(getString(R.string.t5_8));

		List<String> Sec6 = new ArrayList<String>();
		Sec6.add(getString(R.string.t6_1));
		Sec6.add(getString(R.string.t6_2));
		Sec6.add(getString(R.string.t6_3));

		List<String> Sec7 = new ArrayList<String>();
		// Sec7.add(getString(R.string.t7_1));

		// Adding child chap tag data
		List<String> C1 = new ArrayList<String>();
		C1.add(getString(R.string.c1_1));
		C1.add(getString(R.string.c1_2));
		C1.add(getString(R.string.c1_3));
		C1.add(getString(R.string.c1_5));
		C1.add(getString(R.string.c1_6));
		C1.add(getString(R.string.c1_8));
		C1.add(getString(R.string.c1_9));

		List<String> C2 = new ArrayList<String>();
		C2.add(getString(R.string.c2_1));
		C2.add(getString(R.string.c2_2));
		C2.add(getString(R.string.c2_3));
		C2.add(getString(R.string.c2_4));
		C2.add(getString(R.string.c2_5));
		C2.add(getString(R.string.c2_6));
		C2.add(getString(R.string.c2_7));

		List<String> C3 = new ArrayList<String>();
		C3.add(getString(R.string.c3_1));
		C3.add(getString(R.string.c3_2));
		C3.add(getString(R.string.c3_3));
		C3.add(getString(R.string.c3_4));
		C3.add(getString(R.string.c3_5));

		List<String> C4 = new ArrayList<String>();
		C4.add(getString(R.string.c4_1));
		C4.add(getString(R.string.c4_2));
		C4.add(getString(R.string.c4_3));
		C4.add(getString(R.string.c4_4));

		List<String> C5 = new ArrayList<String>();
		C5.add(getString(R.string.c5_1));
		C5.add(getString(R.string.c5_2));
		C5.add(getString(R.string.c5_3));
		C5.add(getString(R.string.c5_4));
		C5.add(getString(R.string.c5_5));
		C5.add(getString(R.string.c5_6));
		C5.add(getString(R.string.c5_7));
		C5.add(getString(R.string.c5_8));

		List<String> C6 = new ArrayList<String>();
		C6.add(getString(R.string.c6_1));
		C6.add(getString(R.string.c6_2));
		C6.add(getString(R.string.c6_3));

		List<String> C7 = new ArrayList<String>();
		// C7.add(getString(R.string.c7_1));

		listDataChild.put(listDataHeader.get(0), Sec1);
		listDataChild.put(listDataHeader.get(1), Sec2); // Header, Child data
		listDataChild.put(listDataHeader.get(2), Sec3);
		listDataChild.put(listDataHeader.get(3), Sec4);
		listDataChild.put(listDataHeader.get(4), Sec5);
		listDataChild.put(listDataHeader.get(5), Sec6);
		listDataChild.put(listDataHeader.get(6), Sec7);

		listDataChildChapTag.put(listDataHeader.get(0), C1);
		listDataChildChapTag.put(listDataHeader.get(1), C2);
		listDataChildChapTag.put(listDataHeader.get(2), C3);
		listDataChildChapTag.put(listDataHeader.get(3), C4);
		listDataChildChapTag.put(listDataHeader.get(4), C5);
		listDataChildChapTag.put(listDataHeader.get(5), C6);
		listDataChildChapTag.put(listDataHeader.get(6), C7);
	}

	@Override
	protected void setupView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_list_class);
		mStrTitle = (String) getTitle();
		listMenu = new ArrayList<String>();
		listMenu.add(getString(R.string.refer));
		listMenu.add(getString(R.string.about));
		listMenu.add(getString(R.string.rate_app));
		listMenu.add(getString(R.string.share_app));
		listMenu.add(getString(R.string.exit));
	}

	@Override
	void searchContents() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handlePositive() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleNeutral() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleNegative() {
		// TODO Auto-generated method stub
		
	}
}
