package com.beyondthecode.timeisnow.data.alarmservice;

import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import io.reactivex.Completable;

public interface AlarmManager {

    Completable setAlarm(Alarm alarm);

    Completable cancelAlarm(Alarm alarm);

    Completable dismissAlarm();

    Completable startAlarm(Alarm alarm);
}
