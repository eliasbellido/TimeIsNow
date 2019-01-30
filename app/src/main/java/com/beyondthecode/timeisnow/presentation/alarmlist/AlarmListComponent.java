package com.beyondthecode.timeisnow.presentation.alarmlist;

import com.beyondthecode.timeisnow.di.components.ApplicationComponent;
import com.beyondthecode.timeisnow.util.FragmentScoped;

import dagger.Component;

@FragmentScoped
@Component(dependencies = ApplicationComponent.class,
            modules = AlarmListPresenterModule.class)
public interface AlarmListComponent {

    void inject(AlarmListFragment alarmListFragment);
}
