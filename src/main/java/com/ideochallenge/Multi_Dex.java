package com.ideochallenge;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Maciej Szalek on 2019-10-30.
 */

public class Multi_Dex extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
