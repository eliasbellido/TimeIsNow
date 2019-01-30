package com.beyondthecode.timeisnow.usecase;

import com.beyondthecode.timeisnow.data.alarmservice.AlarmManager;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import io.reactivex.Completable;

public class SetAlarm implements UseCaseCompletable<Alarm>{

    private final AlarmManager alarmManager;

    public SetAlarm(AlarmManager alarmManager) {
        this.alarmManager = alarmManager;
    }

    @Override
    public Completable runUseCase(Alarm... params) {
        return alarmManager.setAlarm(params[0]);
    }
}
