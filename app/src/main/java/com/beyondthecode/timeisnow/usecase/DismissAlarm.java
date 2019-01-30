package com.beyondthecode.timeisnow.usecase;

import com.beyondthecode.timeisnow.data.alarmservice.AlarmManager;

import io.reactivex.Completable;

public class DismissAlarm implements UseCaseCompletable<Void>{

    private final AlarmManager alarmManager;

    public DismissAlarm(AlarmManager alarmManager) {
        this.alarmManager = alarmManager;
    }

    @Override
    public Completable runUseCase(Void... params) {
        return alarmManager.dismissAlarm();
    }
}
