package com.beyondthecode.timeisnow.data.alarmdatabase;

import com.beyondthecode.timeisnow.data.realmmodel.RealmAlarm;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class AlarmDatabase implements AlarmSource {

    public AlarmDatabase(){

    }

    @Override
    public Completable deleteAlarm(Alarm alarm) {
        return Completable.create(
                (e) -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    RealmQuery<RealmAlarm> query = realm.where(RealmAlarm.class);
                    query.equalTo("alarmId", alarm.getAlarmId());

                    RealmResults<RealmAlarm> result = query.findAll();

                    if(result.size() == 0 ){
                        realm.cancelTransaction();
                        e.onError(new Exception());
                    }else{
                        result.deleteFromRealm(0);
                        realm.commitTransaction();
                        e.onComplete();
                    }

                }
        );
    }

    @Override
    public Completable updateAlarm(Alarm alarm) {
        return Completable.create(
                //Se puede usar Lambda o clasicamente
                new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(CompletableEmitter emitter) throws Exception {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();

                        RealmAlarm realmAlarm = new RealmAlarm();

                        realmAlarm.setAlarmId(alarm.getAlarmId());
                        realmAlarm.setHourOfDay(alarm.getHourOfDay());
                        realmAlarm.setMinute(alarm.getMinute());
                        realmAlarm.setAlarmTitle(alarm.getAlarmTitle());
                        realmAlarm.setActive(alarm.isActive());
                        realmAlarm.setVibrateOnly(alarm.isVibrateOnly());
                        realmAlarm.setRenewAutomatically(alarm.isRenewAutomatically());

                        realm.copyToRealmOrUpdate(realmAlarm);
                        realm.commitTransaction();

                        emitter.onComplete();
                    }
                }
        );
    }

    @Override
    public Flowable<List<Alarm>> getAlarms() {
        return Flowable.create(
                e -> {
                    Realm realm = Realm.getDefaultInstance();

                    RealmQuery<RealmAlarm> query = realm.where(RealmAlarm.class);
                    RealmResults<RealmAlarm> result = query.findAll();

                    List<Alarm> alarmList = new ArrayList<>();

                    if(result.size() == 0){
                        e.onComplete();
                    }else{
                        for(RealmAlarm item : result ){

                            Alarm alarm = new Alarm();

                            alarm.setActive(item.isActive());
                            alarm.setRenewAutomatically(item.isRenewAutomatically());
                            alarm.setVibrateOnly(item.isVibrateOnly());
                            alarm.setHourOfDay(item.getHourOfDay());
                            alarm.setMinute(item.getMinute());
                            alarm.setAlarmTitle(item.getAlarmTitle());
                            alarm.setAlarmId(item.getAlarmId());

                            alarmList.add(alarm);
                        }
                        e.onNext(alarmList);
                    }
                },
                BackpressureStrategy.LATEST
        );
    }

    @Override
    public Flowable<Alarm> getAlarmsById(String reminderId) {
        return Flowable.create(
                e -> {
                    Realm realm = Realm.getDefaultInstance();

                    RealmQuery<RealmAlarm> query = realm.where(RealmAlarm.class);
                    query.equalTo("alarmId", reminderId);

                    RealmResults<RealmAlarm> result = query.findAll();

                    if(result.size()==0){
                        e.onError(new Exception("AlarmNotFoundException"));
                    }else{
                        RealmAlarm realmAlarm = result.get(0);
                        Alarm alarm = new Alarm();

                        alarm.setAlarmId(realmAlarm.getAlarmId());
                        alarm.setActive(realmAlarm.isActive());
                        alarm.setRenewAutomatically(realmAlarm.isRenewAutomatically());
                        alarm.setVibrateOnly(realmAlarm.isVibrateOnly());
                        alarm.setHourOfDay(realmAlarm.getHourOfDay());
                        alarm.setMinute(realmAlarm.getMinute());
                        alarm.setAlarmTitle(realmAlarm.getAlarmTitle());

                        e.onNext(alarm);
                    }
                },
                BackpressureStrategy.LATEST
        );
    }
}
