package com.beyondthecode.timeisnow.presentation.alarmreceiver;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.beyondthecode.timeisnow.R;
import com.beyondthecode.timeisnow.presentation.alarmlist.AlarmListFragment;
import com.beyondthecode.timeisnow.util.ActivityUtils;

public class AlarmReceiverActivity extends AppCompatActivity {

    private static final String ALARM_FRAGMENT = "ALARM_FRAGMENT";
    private static final String ALARM_ID = "ALARM_ID";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


        setContentView(R.layout.activity_alarm_receiver);

        String reminderId = getIntent().getStringExtra(ALARM_ID);

        manager = getSupportFragmentManager();

        AlarmReceiverFragment fragment = (AlarmReceiverFragment) manager.findFragmentByTag(ALARM_FRAGMENT);

        if(fragment == null){
            fragment = AlarmReceiverFragment.newInstance(reminderId);
        }

        ActivityUtils.addFragmentToActivity(
                manager,
                fragment,
                R.id.root_alarm,
                ALARM_FRAGMENT
        );
    }


    //TODO: completar el activity y su fragment
}
