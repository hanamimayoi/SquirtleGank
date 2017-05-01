package com.hanami.squirtle.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hanami.squirtle.app.MyApplication;

/**
 * Created by hanami on 2017/4/16.
 */

public class NetworkUtils {

    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
