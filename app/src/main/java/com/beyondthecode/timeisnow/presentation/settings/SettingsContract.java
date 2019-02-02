package com.beyondthecode.timeisnow.presentation.settings;

import com.beyondthecode.timeisnow.base.BasePresenter;
import com.beyondthecode.timeisnow.base.BaseView;

public interface SettingsContract {

    interface View extends BaseView{
        void startAlarmListActivity();
    }

    interface Presenter extends BasePresenter{
        void onBackButtonPress();
    }
}
