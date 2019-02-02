package com.beyondthecode.timeisnow.presentation.alarmreceiver;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beyondthecode.timeisnow.R;
import com.beyondthecode.timeisnow.TimeisnowApplication;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmReceiverFragment extends Fragment implements AlarmReceiverContract.View {

    private static final String ALARM_ID = "ALARM_ID";
    private String alarmId;

    @Inject
    AlarmReceiverPresenter presenter;

    public AlarmReceiverFragment(){

    }

    public static AlarmReceiverFragment newInstance(String alarmId){
        AlarmReceiverFragment fragment = new AlarmReceiverFragment();
        Bundle args = new Bundle();
        args.putString(ALARM_ID, alarmId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmId = getArguments().getString(ALARM_ID);
        setRetainInstance(true);

        DaggerAlarmReceiverComponent.builder()
                .alarmReceiverPresenterModule(new AlarmReceiverPresenterModule(this))
                .applicationComponent(
                        ((TimeisnowApplication) getActivity().getApplication())
                        .getApplicationComponent()
                )
                .build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);

        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.btn_alarm_dismiss)
    public void dimissAlarmClick(){
        presenter.onAlarmDismissClick();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

         /*
                In order to set up the Presenter properly, it must be supplied with the Id of the
                Alarm which just went off.
                 */
        presenter.start();
    }

    @Override
    public String getAlarmid() {
        return alarmId;
    }

    @Override
    public Alarm getViewModel() {
        return new Alarm(
                alarmId,
                getString(R.string.def_alarm_name),
                false,
                true,
                false,
                12,
                30
        );
    }

    @Override
    public void finishActivity() {
        Activity activity = getActivity();

        if(activity !=  null) activity.finish();
    }

    @Override
    public void makeToast(int message) {
        Toast.makeText(getActivity(),
                message,
                Toast.LENGTH_SHORT)
                .show();
    }
}
