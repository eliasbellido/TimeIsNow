package com.beyondthecode.timeisnow.presentation.alarmdetail;

import com.beyondthecode.timeisnow.base.BasePresenter;
import com.beyondthecode.timeisnow.base.BaseView;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

public interface AlarmDetailContract {

    interface View extends BaseView{
        Alarm getViewModel();

        void setAlarmTitle(String title);

        void setVibrateOnly(boolean active);

        void setRenewAutomatically(boolean active);

        void setPickerTime(int hour, int minute);

        void setCurrentAlarmState(boolean active);

        String getAlarmId();

        void startAlarmListActivity();
    }

    interface Presenter extends BasePresenter{
        void onBackIconPress();

        void onDoneIconPress();
    }
}
