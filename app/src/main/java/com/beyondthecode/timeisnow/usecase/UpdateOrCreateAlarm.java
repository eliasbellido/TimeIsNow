package com.beyondthecode.timeisnow.usecase;

import com.beyondthecode.timeisnow.data.alarmdatabase.AlarmSource;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import io.reactivex.Completable;

public class UpdateOrCreateAlarm implements UseCaseCompletable<Alarm>{

    private final AlarmSource alarmSource;

    public UpdateOrCreateAlarm(AlarmSource alarmSource) {
        this.alarmSource = alarmSource;
    }

    @Override
    public Completable runUseCase(Alarm... params) {
        return alarmSource.updateAlarm(params[0]);
    }
}
