package com.beyondthecode.timeisnow.data.alarmservice;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;

import com.beyondthecode.timeisnow.alarmreceiver.AlarmReceiverActivity;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import java.io.IOException;
import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AlarmService implements AlarmManager{

    private static final String ALARM_ID = "ALARM_ID";

    private final PowerManager.WakeLock wakeLock;
    private MediaPlayer mediaPlayer;
    private final AudioManager audioManager;
    private final Vibrator vibe;
    private final android.app.AlarmManager alarmManager;
    private final Context applicationContext;

    @Inject
    public AlarmService(PowerManager.WakeLock wakeLock, MediaPlayer mediaPlayer, AudioManager audioManager, Vibrator vibe, android.app.AlarmManager alarmManager, Context applicationContext) {
        this.wakeLock = wakeLock;
        this.mediaPlayer = mediaPlayer;
        this.audioManager = audioManager;
        this.vibe = vibe;
        this.alarmManager = alarmManager;
        this.applicationContext = applicationContext;
    }

    @Override
    public Completable setAlarm(Alarm reminder) {

        Calendar alarm = Calendar.getInstance();
        alarm.setTimeInMillis(System.currentTimeMillis());
        alarm.set(Calendar.HOUR_OF_DAY, reminder.getHouOfDay());
        alarm.set(Calendar.MINUTE, reminder.getMinute());

        //asegurse que se está seteando la alarma para más temprano hoy
        checkAlarm(alarm);

        Intent intent = new Intent(applicationContext, AlarmReceiverActivity.class);
        intent.putExtra(ALARM_ID, reminder.getAlarmId());
        PendingIntent alarmIntent = PendingIntent.getActivity(
                applicationContext,
                Integer.parseInt(reminder.getAlarmId()),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if(reminder.isRenewAutomatically()){
            alarmManager.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP, alarm.getTimeInMillis(),
                    android.app.AlarmManager.INTERVAL_DAY, alarmIntent);
        }else{
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, alarm.getTimeInMillis(),
                    alarmIntent);
        }

        return Completable.complete();
    }

    private void checkAlarm(Calendar alarm){
        Calendar now = Calendar.getInstance();
        if(alarm.before(now)){
            long alarmForFollowingDay = alarm.getTimeInMillis() + 86400000L;
            alarm.setTimeInMillis(alarmForFollowingDay);

        }
    }

    @Override
    public Completable cancelAlarm(Alarm alarm) {

        Intent intent = new Intent(applicationContext, AlarmReceiverActivity.class);

        PendingIntent alarmIntent = PendingIntent.getActivity(applicationContext,
                Integer.parseInt(alarm.getAlarmId()),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(alarmIntent);
        return Completable.complete();
    }

    @Override
    public Completable dismissAlarm() {

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if(vibe != null){
            vibe.cancel();
        }

        if(wakeLock != null && wakeLock.isHeld()){
            wakeLock.release();
        }

        return Completable.complete();
    }

    @Override
    public Completable startAlarm(Alarm alarm) {

        wakeLock.acquire();

        if(alarm.isVibrateOnly()){
            vibratePhone();
        }else{
            vibratePhone();
            try{
                playAlarmSound();

                //I tried with IOException but it threw an error
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return Completable.complete();
    }

    //TODO: implemet throws java.io.IOException just in case later.

    private void playAlarmSound(){
        new CountDownTimer(30000,1000){
            public void onTick(long millisUntilFinished){

            }

            public void onFinish(){
                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        };

        mediaPlayer.start();
    }

    private void vibratePhone(){
        long[] vPatterNone = {0, 1000, 2000, 1000, 2000,1000, 2000,1000, 2000};

        // The '-1' here means to vibrate once, as '-1' is out of bounds in the pattern array
        vibe.vibrate(vPatterNone, -1);
    }

    private String getAlarmId(){
        String alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
        if(alert == null){
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString();
            if(alert == null){
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE).toString();
            }
        }

        return alert;
    }
}
