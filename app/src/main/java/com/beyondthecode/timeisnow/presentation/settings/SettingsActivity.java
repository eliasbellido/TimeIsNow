package com.beyondthecode.timeisnow.presentation.settings;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.beyondthecode.timeisnow.R;
import com.beyondthecode.timeisnow.util.ActivityUtils;

public class SettingsActivity extends AppCompatActivity {

    private static final String FRAG_SETTINGS = "FRAG_SETTINGS";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        manager = getSupportFragmentManager();

        SettingsFragment fragment = (SettingsFragment) manager.findFragmentByTag(FRAG_SETTINGS);

        if(fragment == null){
            fragment = SettingsFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                fragment,
                R.id.cont_settings_fragment,
                FRAG_SETTINGS
        );
    }
}
