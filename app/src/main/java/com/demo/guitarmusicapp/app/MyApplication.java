package com.demo.guitarmusicapp.app;

import android.app.Application;
import android.content.Context;

import com.demo.guitarmusicapp.R;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;


public class MyApplication extends Application {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.upgradeCheckPeriod = 60 * 1000;
        //避免影响APP启动速度
        Beta.initDelay = 1 * 1000;
        Beta.smallIconId = R.mipmap.icon;
        Bugly.init(getApplicationContext(), "0c8e1a4565", false);
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        // you must install multiDex whatever tinker is installed!
//        MultiDex.install(base);
//    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

}
