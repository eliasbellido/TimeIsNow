package com.beyondthecode.timeisnow.presentation.alarmlist;

import com.beyondthecode.timeisnow.base.BasePresenter;
import com.beyondthecode.timeisnow.base.BaseView;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import java.util.List;

public interface AlarmListContract {

    interface View extends BaseView{
        void setAlarmListData(List<Alarm> alarmListData);

        void setNoAlarmListDataFound();

        void addNewAlarmToListView(Alarm alarm);

        void startAlarmDetailActivity(String alarmId);

        void startSettingsActivity();

        void showUndoSnackbar();

        void insertAlarmAt(int position, Alarm alarm);
    }

    interface Presenter extends BasePresenter{

        void onAlarmToggled(boolean active, Alarm alarm);

        void onSettingsIconClick();

        void onAlarmSwiped(int index, Alarm alarm);

        void onAlarmIconClick(Alarm alarm);

        void onCreateAlarmButtonClick(int currentNumberOfAlarms, Alarm alarm);

        void onUndoConfirmed();

        void onSnackbarTimeout();
    }
}
