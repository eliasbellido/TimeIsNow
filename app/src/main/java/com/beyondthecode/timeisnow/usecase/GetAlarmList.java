package com.beyondthecode.timeisnow.usecase;

import com.beyondthecode.timeisnow.data.alarmdatabase.AlarmSource;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import java.util.List;

import io.reactivex.Flowable;

public class GetAlarmList implements UseCase<List<Alarm>, Void> {

    private final AlarmSource alarmSource;

    public GetAlarmList(AlarmSource alarmSource) {
        this.alarmSource = alarmSource;
    }

    @Override
    public Flowable<List<Alarm>> runUseCase(Void... params) {
        return alarmSource.getAlarms();
    }
}
