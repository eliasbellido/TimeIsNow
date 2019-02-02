package com.beyondthecode.timeisnow.presentation.settings;

import com.beyondthecode.timeisnow.di.components.ApplicationComponent;
import com.beyondthecode.timeisnow.util.FragmentScoped;

import dagger.Component;

@FragmentScoped
@Component(dependencies = ApplicationComponent.class,
            modules = SettingsPresenterModule.class)
public interface SettingsComponent {

    void inject(SettingsFragment settingsFragment);
}
