package com.lknhac.nlawyer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.lknhac.nlawyer.data.Shakespeare;
import com.lknhac.nlawyer.util.Const;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

@SuppressLint("SetJavaScriptEnabled")
public class ContentActivity extends DrawerLayoutActivity {
	private WebView mWebView;
	private ImageView itemUpToTop;
	public static final int[] SEC_COUNT = { 9, 7, 5, 4, 8, 3, 1 };
	/** The view to show the ad. */
	private AdView adView;

	/* Your ad unit id. Replace with your actual ad unit id. */
//	private static final String AD_UNIT_ID = "ca-app-pub-5056196661864424/7874061193";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Const.isNetworkAvailable(getApplicationContext())) {
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
//			LinearLayout layout = (LinearLayout) findViewById(R.id.llBottom);
//			layout.setVisibility(View.GONE);
		}

		mWebView = (WebView) findViewById(R.id.wv_content);
		mWebView.setWebViewClient(new WebViewClient());

		String url = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			url = "file:///android_asset/" + extras.getString(Const.TITLE);
		} else {
			url = "file:///android_asset/t1_1.htm";
		}
		// url =
		// "http://game.24h.com.vn/game-hay-nhat/pikachu-phien-ban-moi-c131g597b15.html";
		// url = "file:///android_asset/game/testgame.html";
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setLoadsImagesAutomatically(true);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setDisplayZoomControls(false);
		mWebView.loadUrl(url);

		itemUpToTop = (ImageView) findViewById(R.id.itemUpToTop);

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				itemUpToTop.setVisibility(View.VISIBLE);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// error
			}
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(url.equals(getString(R.string.url))){
					Intent send = new Intent(Intent.ACTION_SENDTO);
					String uriText = "mailto:" + Uri.encode(getString(R.string.email)) + 
					          "?subject=" + Uri.encode("the subject") + 
					          "&body=" + Uri.encode("the body of the message");
					Uri uri = Uri.parse(uriText);
					send.setData(uri);
					startActivity(Intent.createChooser(send, "Send mail..."));
					return true;
				}
				return super.shouldOverrideUrlLoading(view, url);
			}
		});

		itemUpToTop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mWebView.scrollTo(0, 0);
			}
		});
	}

	// search function
	private static final int SEARCH_MENU_ID = Menu.FIRST;
	private LinearLayout container;
	private ImageView nextButton, previousButton, closeButton;
	private EditText findBox;

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// super.onCreateOptionsMenu(menu);
	//
	// menu.add(0, SEARCH_MENU_ID, 0, "Search");
	//
	// return true;
	// }
	//
	// @Override
	// public boolean onPrepareOptionsMenu(Menu menu) {
	// super.onPrepareOptionsMenu(menu);
	// return true;
	// }

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case SEARCH_MENU_ID:
	// search();
	// return true;
	// }
	// return true;
	// }

	public void search() {
		container = (LinearLayout) findViewById(R.id.layoutId);

		nextButton = new ImageView(this);
		nextButton.setBackgroundResource(R.drawable.ico_next);

		nextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mWebView.findNext(true);
			}
		});
		container.addView(nextButton);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) nextButton
				.getLayoutParams();
		params.setMargins(10, 0, 10, 0); // substitute parameters for left, top,
											// right, bottom
		nextButton.setLayoutParams(params);

		previousButton = new ImageView(this);

		previousButton.setBackgroundResource(R.drawable.ico_previous);
		previousButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mWebView.findNext(false);
			}
		});
		container.addView(previousButton);
		LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) previousButton
				.getLayoutParams();
		params2.setMargins(0, 0, 10, 0); // substitute parameters for left, top,
											// right, bottom
		previousButton.setLayoutParams(params2);

		findBox = new EditText(this);
		// findBox.setMinEms(20);
		findBox.setMinWidth(250);
		findBox.setSingleLine(true);
		findBox.setHint(getString(R.string.search));

		findBox.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& ((keyCode == KeyEvent.KEYCODE_ENTER))) {
					mWebView.findAll(findBox.getText().toString());

					try {
						Method m = WebView.class.getMethod("setFindIsUp",
								Boolean.TYPE);
						m.invoke(mWebView, true);
					} catch (Exception ignored) {
					}
				}
				return false;
			}
		});

		container.addView(findBox);

		closeButton = new ImageView(this);
		closeButton.setBackgroundResource(R.drawable.ico_closed);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				container.removeAllViews();

			}
		});
		container.addView(closeButton);
	}

	// private class MyBrowser extends WebViewClient {
	// @Override
	// public boolean shouldOverrideUrlLoading(WebView view, String url) {
	// view.loadUrl(url);
	// return true;
	// }
	// }

	@Override
	protected void setupView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_content);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mStrTitle = extras.getString(Const.CONTENTS);
			listMenu = new ArrayList<String>();
			listMenu.add(getString(R.string.search));
			if (extras.getInt(Const.SEC_NUM) != 0) {
				SEC_NUM = extras.getInt(Const.SEC_NUM);
				getSection(listMenu, extras.getInt(Const.SEC_NUM));
			}
			listMenu.add(getString(R.string.refer));
			listMenu.add(getString(R.string.about));
			listMenu.add(getString(R.string.rate_app));
			listMenu.add(getString(R.string.share_app));
			listMenu.add(getString(R.string.back));
		}
	}

	private void getSection(List<String> listMenu, int sec) {

		switch (sec) {
		case 1:
			getListMenu(listMenu, Shakespeare.SEC_1);
			break;
		case 2:
			getListMenu(listMenu, Shakespeare.SEC_2);
			break;
		case 3:
			getListMenu(listMenu, Shakespeare.SEC_3);
			break;
		case 4:
			getListMenu(listMenu, Shakespeare.SEC_4);
			break;
		case 5:
			getListMenu(listMenu, Shakespeare.SEC_5);
			break;
		case 6:
			getListMenu(listMenu, Shakespeare.SEC_6);
			break;
		case 7:
			getListMenu(listMenu, Shakespeare.SEC_7);
			break;
		default:
			break;
		}
	}

	private void getListMenu(List<String> listMenu, String[] listSec) {
		for (String item : listSec) {
			listMenu.add(item);
		}
	}

	@Override
	void searchContents() {
		// TODO Auto-generated method stub
		search();
	}

}
