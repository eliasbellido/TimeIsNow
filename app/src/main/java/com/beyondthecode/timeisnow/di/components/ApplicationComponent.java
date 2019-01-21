package com.beyondthecode.timeisnow.di.components;

import android.content.Context;
import android.media.AudioManager;
import android.os.PowerManager;
import android.os.Vibrator;

import com.beyondthecode.timeisnow.data.alarmdatabase.AlarmSource;
import com.beyondthecode.timeisnow.data.alarmservice.AlarmManager;
import com.beyondthecode.timeisnow.di.modules.ApplicationModule;
import com.beyondthecode.timeisnow.util.BaseSchedulerProvider;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();
    PowerManager.WakeLock wakeLock();
    AudioManager audioManager();
    Vibrator vibrator();
    AlarmManager alarmManager();
    AlarmSource alarmSource();
    BaseSchedulerProvider baseSchedulerProvider();

}
