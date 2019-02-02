package com.beyondthecode.timeisnow.presentation.alarmreceiver;

import dagger.Module;
import dagger.Provides;

@Module
public class AlarmReceiverPresenterModule {

    //List what you would normally pass in as arguments to a Presenter's constructor
    private final AlarmReceiverContract.View view;

    public AlarmReceiverPresenterModule(AlarmReceiverContract.View view){
        this.view = view;
    }

    @Provides
    AlarmReceiverContract.View provideAlarmReceiverView(){
        return view;
    }
}
