package com.brunogcpinheiro.igrejadaunidade;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by Bruno on 26/10/2016.
 */

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this).init();
    }
}
