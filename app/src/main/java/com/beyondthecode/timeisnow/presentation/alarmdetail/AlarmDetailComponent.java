package com.beyondthecode.timeisnow.presentation.alarmdetail;

import com.beyondthecode.timeisnow.di.components.ApplicationComponent;
import com.beyondthecode.timeisnow.util.FragmentScoped;

import dagger.Component;

@FragmentScoped
@Component(dependencies = ApplicationComponent.class,
            modules = AlarmDetailPresenterModule.class)
public interface AlarmDetailComponent {

    void inject(AlarmDetailFragment alarmDetailFragment);
}
