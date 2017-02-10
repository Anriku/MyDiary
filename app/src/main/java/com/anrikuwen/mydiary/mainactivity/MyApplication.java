package com.anrikuwen.mydiary.mainactivity;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by 10393 on 2017/2/10.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
