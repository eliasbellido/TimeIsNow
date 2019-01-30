package com.beyondthecode.timeisnow.presentation.alarmlist;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.beyondthecode.timeisnow.R;
import com.beyondthecode.timeisnow.util.ActivityUtils;

public class AlarmListActivity extends AppCompatActivity {

    private static final String FRAG_ALARM_LIST = "FRAG_ALARM_LIST";
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        manager = getSupportFragmentManager();

        AlarmListFragment fragment = (AlarmListFragment) manager.findFragmentByTag(FRAG_ALARM_LIST);

        if(fragment == null){
            Log.d("acá!", "el fragment es null");
            fragment = AlarmListFragment.newInstance();
        }
        Log.d("acá!", "el fragment está siendo agregado..");
        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                fragment,
                R.id.cont_alarm_list_fragment,
                FRAG_ALARM_LIST
        );

        Log.d("acá!", "el fragment se agregó al activity");


    }
}
