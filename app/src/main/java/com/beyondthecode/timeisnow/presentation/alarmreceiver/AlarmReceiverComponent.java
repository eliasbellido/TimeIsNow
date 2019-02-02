package com.beyondthecode.timeisnow.presentation.alarmreceiver;

import com.beyondthecode.timeisnow.di.components.ApplicationComponent;
import com.beyondthecode.timeisnow.util.FragmentScoped;

import dagger.Component;

@FragmentScoped
@Component(
        dependencies = ApplicationComponent.class,
        modules = AlarmReceiverPresenterModule.class
)
public interface AlarmReceiverComponent {
    void inject(AlarmReceiverFragment alarmReceiverFragment);
}
