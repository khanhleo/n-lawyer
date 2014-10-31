package com.lknhac.nlawyer.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Const {
	public static final String CONTENTS ="contents";
	public static final String TITLE ="title";
	public static final String SEC_NUM ="sec_num";
	public static final String AD_UNIT_ID = "ca-app-pub-5056196661864424/7874061193";
	
	public static final String NAME = "rate_me_maybe";
	public static final String DONT_SHOW_AGAIN = "PREF_DONT_SHOW_AGAIN";
	
	public static boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
