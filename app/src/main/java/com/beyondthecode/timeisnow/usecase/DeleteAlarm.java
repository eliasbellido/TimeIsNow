package com.beyondthecode.timeisnow.usecase;

import com.beyondthecode.timeisnow.data.alarmdatabase.AlarmSource;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import io.reactivex.Completable;

public class DeleteAlarm implements UseCaseCompletable<Alarm>{

    private final AlarmSource alarmSource;

    public DeleteAlarm(AlarmSource alarmSource){
        this.alarmSource = alarmSource;
    }

    @Override
    public Completable runUseCase(Alarm... alarm) {
        return alarmSource.deleteAlarm(alarm[0]);
    }
}
