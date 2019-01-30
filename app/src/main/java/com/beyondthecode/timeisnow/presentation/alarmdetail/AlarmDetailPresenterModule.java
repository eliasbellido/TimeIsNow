package com.beyondthecode.timeisnow.presentation.alarmdetail;

import dagger.Module;
import dagger.Provides;

@Module
public class AlarmDetailPresenterModule {
    //List what you would normally pass in as arguments to a Presenter's constructor
    private final AlarmDetailContract.View view;

    public AlarmDetailPresenterModule(AlarmDetailContract.View view){
        this.view = view;
    }

    @Provides
    AlarmDetailContract.View provideAlarmDetailView(){
        return view;
    }
}
