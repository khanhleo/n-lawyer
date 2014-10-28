package com.lknhac.nlawyer;

import java.util.ArrayList;
import java.util.List;

import com.lknhac.nlawyer.data.Shakespeare;
import com.lknhac.nlawyer.util.Const;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class ContentActivity extends DrawerLayoutActivity {
	private WebView mWebView;
	public static final int[] SEC_COUNT = { 9, 7, 5, 4, 8, 3, 1 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			if (extras.getInt(Const.SEC_NUM) != 0) {
				SEC_NUM = extras.getInt(Const.SEC_NUM);
				getSection(listMenu, extras.getInt(Const.SEC_NUM));
			}
			listMenu.add(getString(R.string.refer));
			listMenu.add(getString(R.string.about));
			listMenu.add(getString(R.string.exit));
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

}
