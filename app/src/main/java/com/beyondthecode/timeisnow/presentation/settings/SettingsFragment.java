package com.beyondthecode.timeisnow.presentation.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.beyondthecode.timeisnow.R;
import com.beyondthecode.timeisnow.TimeisnowApplication;
import com.beyondthecode.timeisnow.presentation.alarmlist.AlarmListActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsFragment extends Fragment implements SettingsContract.View {

    @Inject
    SettingsPresenter settingsPresenter;

    @BindView(R.id.imb_settings_back)
    ImageButton back;

    public SettingsFragment(){

    }

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        DaggerSettingsComponent.builder()
                .settingsPresenterModule(new SettingsPresenterModule(this))
                .applicationComponent(
                        ((TimeisnowApplication)getActivity().getApplication())
                        .getApplicationComponent()
                )
                .build().inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        ButterKnife.bind(this,v);

        return v;

    }

    @OnClick(R.id.imb_settings_back)
    public void settingsBack(){
        settingsPresenter.onBackButtonPress();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void startAlarmListActivity() {
        startActivity(new Intent(getActivity(), AlarmListActivity.class));
    }

    @Override
    public void makeToast(int message) {
        Toast.makeText(getActivity(),
                message,
                Toast.LENGTH_SHORT)
                .show();
    }
}
