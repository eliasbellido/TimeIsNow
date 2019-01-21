package com.beyondthecode.timeisnow.data.alarmdatabase;

import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface AlarmSource {

    Completable deleteAlarm(Alarm alarm);

    Completable updateAlarm(Alarm alarm);

    Flowable<List<Alarm>> getAlarms();

    Flowable<Alarm> getAlarmsById(String reminderId);
}
