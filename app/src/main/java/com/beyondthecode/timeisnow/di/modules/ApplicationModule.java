package com.beyondthecode.timeisnow.di.modules;

import android.app.AlarmManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;

import com.beyondthecode.timeisnow.data.alarmdatabase.AlarmDatabase;
import com.beyondthecode.timeisnow.data.alarmdatabase.AlarmSource;
import com.beyondthecode.timeisnow.data.alarmservice.AlarmService;
import com.beyondthecode.timeisnow.util.BaseSchedulerProvider;
import com.beyondthecode.timeisnow.util.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {

    private final Context applicationContext;
    private final PowerManager.WakeLock wakeLock;
    private final Vibrator vibrator;
    private final AudioManager audioManager;
    private final AlarmManager alarmManager;
    private final MediaPlayer mediaPlayer;

    public ApplicationModule(Context application){
        this.applicationContext = application;
        this.wakeLock = ((PowerManager) applicationContext
                .getSystemService(Context.POWER_SERVICE))
                .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"Alarm:");
        this.audioManager = ((AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE));
        this.vibrator = ((Vibrator) applicationContext.getSystemService(Context.VIBRATOR_SERVICE));
        this.alarmManager = ((AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE));
        this.mediaPlayer = MediaPlayer.create(application, Settings.System.DEFAULT_ALARM_ALERT_URI);

    }

    @Provides
    @Singleton
    Context provideContext(){
        return applicationContext;
    }

    @Provides
    @Singleton
    PowerManager.WakeLock provideWakeLock(){
        return wakeLock;
    }

    @Provides
    @Singleton
    AudioManager provideAudioManager(){
        return audioManager;
    }

    @Provides
    @Singleton
    MediaPlayer provideMediaPlayer(){
        return mediaPlayer;
    }

    @Provides
    @Singleton
    Vibrator provideVibrator(){
        return vibrator;
    }

    @Provides
    @Singleton
    AlarmManager provideAndroidAlarmManager(){
        return alarmManager;
    }

    @Provides
    @Singleton
    BaseSchedulerProvider provideScheduler(){
        return new SchedulerProvider();
    }

    @Provides
    @Singleton
    com.beyondthecode.timeisnow.data.alarmservice.AlarmManager provideAlarmManager(){
        return new AlarmService(
                wakeLock,
                mediaPlayer,
                audioManager,
                vibrator,
                alarmManager,
                applicationContext
        );
    }

    @Singleton
    @Provides
    AlarmSource provideAlarmSource() {
        return new AlarmDatabase();
    }


}
