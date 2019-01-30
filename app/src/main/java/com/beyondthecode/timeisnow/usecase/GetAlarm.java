package com.beyondthecode.timeisnow.usecase;

import com.beyondthecode.timeisnow.data.alarmdatabase.AlarmSource;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import io.reactivex.Flowable;

public class GetAlarm implements UseCase<Alarm, String> {

    /**
     *Alarm Service is a Facade/Repository Pattern which Abstracts Realm from the rest of the
     App
     */

    private final AlarmSource alarmSource;

    public GetAlarm(AlarmSource alarmSource){
        this.alarmSource = alarmSource;
    }


    @Override
    public Flowable<Alarm> runUseCase(String... params) {
        return alarmSource.getAlarmsById(params[0]);
    }
}
