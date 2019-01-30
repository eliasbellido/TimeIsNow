package com.beyondthecode.timeisnow.usecase;

import com.beyondthecode.timeisnow.data.alarmservice.AlarmManager;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import io.reactivex.Completable;

public class CancelAlarm implements UseCaseCompletable<Alarm> {

    private final AlarmManager alarmManager;

    public CancelAlarm(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }
    @Override
    public Completable runUseCase(Alarm... alarm) {
        return alarmManager.cancelAlarm(alarm[0]);
    }
}
