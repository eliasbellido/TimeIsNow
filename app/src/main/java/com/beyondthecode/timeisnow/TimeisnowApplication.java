package com.beyondthecode.timeisnow;

import android.app.Application;

import com.beyondthecode.timeisnow.di.components.ApplicationComponent;
import com.beyondthecode.timeisnow.di.components.DaggerApplicationComponent;
import com.beyondthecode.timeisnow.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TimeisnowApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();
        initLeakCanary();

        ApplicationModule applicationModule = new ApplicationModule(
                getApplicationContext()
        );

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(applicationModule)
                .build();


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

    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}
