package com.beyondthecode.timeisnow.presentation.settings;

import javax.inject.Inject;

public class SettingsPresenter implements SettingsContract.Presenter{


    private final SettingsContract.View view;

    @Inject
    public SettingsPresenter(SettingsContract.View view){
        this.view = view;
    }
    @Override
    public void onBackButtonPress() {
        view.startAlarmListActivity();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
