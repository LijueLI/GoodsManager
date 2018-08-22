package com.pra.haoye.goodsmanager;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class LeakCanaryApplication extends Application {

    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher= setupLeakCanary();
    }
    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        LeakCanaryApplication leakApplication = (LeakCanaryApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }
}