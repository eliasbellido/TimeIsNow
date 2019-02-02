package com.beyondthecode.timeisnow.presentation.settings;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsPresenterModule {

    private final SettingsContract.View view;

    public SettingsPresenterModule(SettingsContract.View view){
        this.view = view;
    }

    @Provides
    SettingsContract.View provideSettingsView(){
        return view;
    }
}
