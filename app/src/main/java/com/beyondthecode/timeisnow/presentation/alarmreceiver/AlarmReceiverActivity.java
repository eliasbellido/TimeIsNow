package com.beyondthecode.timeisnow.presentation.alarmreceiver;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.beyondthecode.timeisnow.R;
import com.beyondthecode.timeisnow.presentation.alarmlist.AlarmListFragment;

public class AlarmReceiverActivity extends AppCompatActivity {

    private static final String FRAG_ALARM_LIST = "FRAG_ALARM_LIST";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_receiver);

        manager = getSupportFragmentManager();

        AlarmListFragment fragment = (AlarmListFragment) manager.findFragmentByTag(FRAG_ALARM_LIST);

        if(fragment == null){

        }
    }


    //TODO: completar el activity y su fragment
}
