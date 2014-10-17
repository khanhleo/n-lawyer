package com.lknhac.nlawyer;

import com.lknhac.nlawyer.util.Const;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class ContentActivity extends DrawerLayoutActivity {
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mWebView = (WebView) findViewById(R.id.wv_content);
		mWebView.setWebViewClient(new WebViewClient());

		String url = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			url = "file:///android_asset/" + extras.getString(Const.CONTENTS);
		} else {
			url = "file:///android_asset/t_1.html";
		}
		//url = "http://game.24h.com.vn/game-hay-nhat/pikachu-phien-ban-moi-c131g597b15.html";
		//url = "file:///android_asset/game/testgame.html";
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setLoadsImagesAutomatically(true);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		mWebView.getSettings().setBuiltInZoomControls(true);
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
			mStrTitle = extras.getString(Const.TITLE);
		}
	}
}
