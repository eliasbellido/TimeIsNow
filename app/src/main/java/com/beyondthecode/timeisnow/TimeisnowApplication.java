package com.beyondthecode.timeisnow;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TimeisnowApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();
        initLeakCanary();
    }

    private void initRealm(){
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    private void initLeakCanary(){
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }
}
