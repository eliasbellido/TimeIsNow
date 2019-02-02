package com.beyondthecode.timeisnow.presentation.alarmreceiver;

import com.beyondthecode.timeisnow.base.BasePresenter;
import com.beyondthecode.timeisnow.base.BaseView;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

public interface AlarmReceiverContract {

    interface View extends BaseView{
        String getAlarmid();

        Alarm getViewModel();

        void finishActivity();
    }

    interface Presenter extends BasePresenter{
        void onAlarmDismissClick();
    }
}
