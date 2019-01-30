package com.beyondthecode.timeisnow.presentation.alarmlist;

import dagger.Module;
import dagger.Provides;

/**
 * This is a feature level Module. It satisfies the Presenter's
 * Dependency on the View Interface.
 */
@Module
public class AlarmListPresenterModule {

    //List what you would normally pass in as arguments to a Presenter's constructor

    private final AlarmListContract.View view;

    public AlarmListPresenterModule(AlarmListContract.View view){
        this.view = view;
    }

    @Provides
    AlarmListContract.View provideAlarmListView(){
        return view;
    }
}
