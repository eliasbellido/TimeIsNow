package com.beyondthecode.timeisnow.presentation.alarmlist;

import com.beyondthecode.timeisnow.R;
import com.beyondthecode.timeisnow.data.alarmdatabase.AlarmSource;
import com.beyondthecode.timeisnow.data.alarmservice.AlarmManager;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;
import com.beyondthecode.timeisnow.usecase.CancelAlarm;
import com.beyondthecode.timeisnow.usecase.DeleteAlarm;
import com.beyondthecode.timeisnow.usecase.GetAlarmList;
import com.beyondthecode.timeisnow.usecase.SetAlarm;
import com.beyondthecode.timeisnow.usecase.UpdateOrCreateAlarm;
import com.beyondthecode.timeisnow.util.BaseSchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class AlarmListPresenter implements AlarmListContract.Presenter {

    private final AlarmListContract.View view;
    private final BaseSchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;

    //Use cases
    private final GetAlarmList getAlarmList;
    private final UpdateOrCreateAlarm updateOrCreateAlarm;
    private final DeleteAlarm deleteAlarm;
    private final SetAlarm setAlarm;
    private final CancelAlarm cancelAlarm;

    //TODO: this can be better

    private Alarm temporaryAlarm;
    private int temporaryAlarmPosition;

    @Inject
    public AlarmListPresenter(AlarmListContract.View view,
                              AlarmSource alarmSource,
                              AlarmManager alarmManager,
                              BaseSchedulerProvider schedulerProvider
                              ) {

        this.getAlarmList = new GetAlarmList(alarmSource);
        this.updateOrCreateAlarm = new UpdateOrCreateAlarm(alarmSource);
        this.deleteAlarm = new DeleteAlarm(alarmSource);
        this.setAlarm = new SetAlarm(alarmManager);
        this.cancelAlarm = new CancelAlarm(alarmManager);

        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();

    }

    @Override
    public void onAlarmToggled(boolean active, Alarm alarm) {
        if(active != alarm.isActive()){
            alarm.setActive(active);

            compositeDisposable.add(
                    updateOrCreateAlarm.runUseCase(alarm)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver(){

                            @Override
                            public void onComplete() {
                                if(active)
                                    onAlarmSet(alarm);
                                else
                                    onAlarmCancelled(alarm);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.makeToast(R.string.error_database_write_failure);
                            }
                        })
            );
        }
    }

    private void onAlarmSet(Alarm alarm){
        compositeDisposable.add(
                setAlarm.runUseCase(alarm)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver(){

                            @Override
                            public void onComplete() {
                                view.makeToast(R.string.msg_alarm_activated);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.makeToast(R.string.error_managing_alarm);
                            }
                        })
        );
    }

    private void onAlarmCancelled(Alarm alarm){
        compositeDisposable.add(
                cancelAlarm.runUseCase(alarm)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .subscribeWith(new DisposableCompletableObserver(){

                                @Override
                                public void onComplete() {
                                    view.makeToast(R.string.msg_alarm_deactivated);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    view.makeToast(R.string.error_managing_alarm);
                                }
                            })
        );
    }
    @Override
    public void onSettingsIconClick() {
        view.startSettingsActivity();
    }

    @Override
    public void onAlarmSwiped(int index, Alarm alarm) {
        alarm.setActive(false);
        temporaryAlarm = alarm;
        temporaryAlarmPosition = index;

        compositeDisposable.add(
                deleteAlarm.runUseCase(alarm)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver(){

                            @Override
                            public void onComplete() {
                                view.showUndoSnackbar();
                                onAlarmCancelled(alarm);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.makeToast(R.string.error_database_connection_failure);
                                view.insertAlarmAt(index, alarm);
                            }
                        })
        );
    }

    @Override
    public void onAlarmIconClick(Alarm alarm) {

        view.startAlarmDetailActivity(alarm.getAlarmId());
    }

    @Override
    public void onCreateAlarmButtonClick(int currentNumberOfAlarms, Alarm alarm) {
        if(currentNumberOfAlarms<5){
            compositeDisposable.add(
                    updateOrCreateAlarm.runUseCase(alarm)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                    .subscribeWith(
                            new DisposableCompletableObserver(){

                                @Override
                                public void onComplete() {
                                    getReminders();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    view.makeToast(R.string.error_database_write_failure);
                                }
                            }
                    )
            );
        }else{
            view.makeToast(R.string.msg_alarm_limit_reached);
        }
    }

    @Override
    public void onUndoConfirmed() {

        if(temporaryAlarm != null){

            compositeDisposable.add(
                    updateOrCreateAlarm.runUseCase(temporaryAlarm)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.ui())
                                .subscribeWith(
                                        new DisposableCompletableObserver(){

                                            @Override
                                            public void onComplete() {
                                                view.insertAlarmAt(temporaryAlarmPosition, temporaryAlarm);
                                                temporaryAlarm = null;
                                                temporaryAlarmPosition = 0;
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                view.makeToast(R.string.error_database_write_failure);
                                            }
                                        }
                                )
            );
        }else{
            view.makeToast(R.string.error_unable_to_retrieve_alarm);
        }

    }

    @Override
    public void onSnackbarTimeout() {
        temporaryAlarm = null;
        temporaryAlarmPosition = 0;
    }

    @Override
    public void start() {
        getReminders();
    }

    @Override
    public void stop() {
        compositeDisposable.clear();
    }

    /**
     * Checks Repository for any existing reminders.
     * returns one of:
     * List of 1-5 Reminders : Display Reminders to User
     * Nothing : Display create RealmAlarm Prompt to User
     * error : Display database error
     */
    private void getReminders(){
        compositeDisposable.add(
                getAlarmList.runUseCase()
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribeWith(new DisposableSubscriber<List<Alarm>>(){

                        @Override
                        public void onNext(List<Alarm> alarms) {
                            view.setAlarmListData(alarms);
                        }

                        @Override
                        public void onError(Throwable t) {
                            view.makeToast(R.string.error_database_connection_failure);
                        }

                        @Override
                        public void onComplete() {
                            view.setNoAlarmListDataFound();
                        }
                    })
        );
    }
}
