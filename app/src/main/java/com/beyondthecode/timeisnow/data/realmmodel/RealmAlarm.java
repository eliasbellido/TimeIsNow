package com.beyondthecode.timeisnow.data.realmmodel;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class RealmAlarm implements RealmModel {

    @PrimaryKey
    private String alarmId;

    private String alarmTitle;
    private boolean active;
    private boolean vibrateOnly;
    private boolean renewAutomatically;
    private int hourOfDay;
    private int minute;

    public RealmAlarm(){

    }

    public RealmAlarm(String alarmId, String alarmTitle, boolean active, boolean vibrateOnly, boolean renewAutomatically, int hourOfDay, int minute) {
        this.alarmId = alarmId;
        this.alarmTitle = alarmTitle;
        this.active = active;
        this.vibrateOnly = vibrateOnly;
        this.renewAutomatically = renewAutomatically;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    public RealmAlarm(String alarmId) {
        this.alarmId = alarmId;
        this.alarmTitle = "Nueva Alarma";
        this.active = false;
        this.vibrateOnly = false;
        this.renewAutomatically = false;
        this.hourOfDay = 12;
        this.minute = 0;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isVibrateOnly() {
        return vibrateOnly;
    }

    public void setVibrateOnly(boolean vibrateOnly) {
        this.vibrateOnly = vibrateOnly;
    }

    public boolean isRenewAutomatically() {
        return renewAutomatically;
    }

    public void setRenewAutomatically(boolean renewAutomatically) {
        this.renewAutomatically = renewAutomatically;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
