package com.beyondthecode.timeisnow.presentation.alarmlist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondthecode.timeisnow.R;
import com.beyondthecode.timeisnow.TimeisnowApplication;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;
import com.beyondthecode.timeisnow.presentation.alarmdetail.AlarmDetailActivity;
import com.beyondthecode.timeisnow.util.TimeConverter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AlarmListFragment extends Fragment implements AlarmListContract.View{

    private static final String ALARM_TO_BE_EDITED = "ALARM_TO_BE_EDITED";
    private ArrayList<Alarm> alarms;
    private AlarmListAdapter adapter;

    @BindView(R.id.lst_alarm_list)
    RecyclerView alarmList;

    @BindView(R.id.fab_alarms)
    FloatingActionButton fabulous;

    @BindView(R.id.lbl_alarm_prompt)
    TextView prompt;

    @BindView(R.id.btn_alarm_list_settings)
    ImageButton settings;

    @Inject
    AlarmListPresenter presenter;

    public AlarmListFragment(){

    }

    public static AlarmListFragment newInstance(){
        return new AlarmListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        DaggerAlarmListComponent.builder()
                .alarmListPresenterModule(new AlarmListPresenterModule(this))
                .applicationComponent(
                        ((TimeisnowApplication) getActivity().getApplication())
                                .getApplicationComponent()
                )
                .build().inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm_list, container, false);

        ButterKnife.bind(this, v);

        alarms = new ArrayList<>();

        initRecyclerView();



        return v;
    }

    @OnClick(R.id.fab_alarms)
    public void onFabClick(){
        presenter.onCreateAlarmButtonClick(
                alarms.size(),
                new Alarm(
                        getDate(),
                        getString(R.string.def_alarm_name),
                        false,
                        true,
                        false,
                        12,
                        30
                )
        );
    }
    @OnClick(R.id.btn_alarm_list_settings)
    public void onIconClick(){
        presenter.onSettingsIconClick();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.stop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /*
    * Context may be risky here during orientation changes. Check for such
    * use cases during tests.
    * */
    @Override
    public void setAlarmListData(List<Alarm> alarms) {
        prompt.setVisibility(View.INVISIBLE);
        alarmList.setVisibility(View.VISIBLE);

        if(!this.alarms.isEmpty()){
            this.alarms.clear();
            adapter.notifyDataSetChanged();
        }

        for(Alarm alarm : alarms){
            //add alarm to fragment list, and inform adapter of this change
            this.alarms.add(alarm);
            adapter.notifyItemInserted(this.alarms.lastIndexOf(alarm));
        }
    }

    @Override
    public void setNoAlarmListDataFound() {
        alarmList.setVisibility(View.INVISIBLE);
        prompt.setVisibility(View.VISIBLE);
    }

    @Override
    public void addNewAlarmToListView(Alarm alarm) {
        if(alarms.size() == 0){
            initRecyclerView();
            alarmList.setVisibility(View.VISIBLE);
            prompt.setVisibility(View.INVISIBLE);
        }

        alarms.add(alarm);
        adapter.notifyItemInserted(this.alarms.lastIndexOf(alarm));
    }

    @Override
    public void startAlarmDetailActivity(String alarmId) {
        Intent intent = new Intent(getActivity(), AlarmDetailActivity.class);
        intent.putExtra(ALARM_TO_BE_EDITED, alarmId);
        startActivity(intent);
    }

    @Override
    public void startSettingsActivity() {

    }

    @Override
    public void showUndoSnackbar() {
        Snackbar.make(
                getView().findViewById(R.id.root_alarm_list_fragment),
                R.string.action_delete_item,
                Snackbar.LENGTH_LONG
        )
                .setAction(
                        R.string.action_undo,
                        v -> {
                            presenter.onUndoConfirmed();
                        })
                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        presenter.onSnackbarTimeout();
                    }
                })
                .show();
    }

    @Override
    public void insertAlarmAt(int position, Alarm alarm) {
        alarms.add(position, alarm);
        adapter.notifyItemInserted(position);
    }

    @Override
    public void makeToast(int message) {
        Toast.makeText(getActivity(),
                message,
                Toast.LENGTH_SHORT
        ).show();
    }

    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        String date =
                "" + calendar.get(Calendar.DAY_OF_YEAR) +
                        "" + calendar.get(Calendar.HOUR_OF_DAY) +
                        "" + calendar.get(Calendar.MINUTE) +
                        "" + calendar.get(Calendar.SECOND);

        return date;
    }

    /*---------------------RecyclerView Boilerplate-------------*/

    private void initRecyclerView(){
        adapter = new AlarmListAdapter(getActivity());
        alarmList.setLayoutManager(new LinearLayoutManager(getActivity()));
        alarmList.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(alarmList);

        alarmList.addItemDecoration(new CustomItemDecorator(getActivity()));
    }

    public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ALViewHolder>{

        private LayoutInflater inflater;

        public AlarmListAdapter(Context context){
            inflater = LayoutInflater.from(context);
        }
        @NonNull
        @Override
        public ALViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = inflater.inflate(R.layout.item_alarm_widget, parent, false);
            return new ALViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ALViewHolder holder, int position) {
            Alarm item = alarms.get(position);
            holder.alarmTitle.setText(item.getAlarmTitle());

            try {
                holder.alarmTitle.setText(
                        TimeConverter.converTime(
                                item.getHourOfDay(),
                                item.getMinute())
                );
            }catch (ParseException e){
                holder.alarmTime.setText(getString(R.string.default_time));
            }

            if(item.isActive()){
                holder.alarmStateLabel.setText(R.string.on);
            }else{
                holder.alarmStateLabel.setText(R.string.off);
            }

            holder.alarmStateSwitch.setChecked(item.isActive());
        }

        @Override
        public int getItemCount() {
            return alarms.size();
        }

        class ALViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            @BindView(R.id.lbl_alarm_title)
            TextView alarmTitle;

            @BindView(R.id.lbl_alarm_time)
            TextView alarmTime;

            @BindView(R.id.lbl_alarm_activation)
            TextView alarmStateLabel;

            @BindView(R.id.im_clock)
            ImageView alarmIcon;

            @BindView(R.id.swi_alarm_activation)
            SwitchCompat alarmStateSwitch;

            public ALViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

                alarmIcon.setOnClickListener(this);
                alarmStateLabel.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int id = v.getId();

                if(id == R.id.swi_alarm_activation){
                    if(alarmStateSwitch.isChecked()){
                        alarmStateLabel.setText(R.string.on);
                        presenter.onAlarmToggled(
                                true,
                                alarms.get(this.getAdapterPosition())
                        );
                    }else{
                        alarmStateLabel.setText(R.string.off);
                        presenter.onAlarmToggled(
                                false,
                                alarms.get(this.getAdapterPosition())
                        );
                    }
                }else if (id == R.id.im_clock){
                    presenter.onAlarmIconClick(
                            alarms.get(this.getAdapterPosition())
                    );
                }
            }
        }
    }

    private ItemTouchHelper.Callback createHelperCallback(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

           //it's not used, as the first parameter above is 0
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                presenter.onAlarmSwiped(
                        position,
                        alarms.get(position)
                );

                alarms.remove(position);
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };

        return simpleItemTouchCallback;
    }
}
