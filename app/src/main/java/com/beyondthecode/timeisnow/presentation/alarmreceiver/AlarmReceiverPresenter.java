package com.beyondthecode.timeisnow.presentation.alarmreceiver;

import com.beyondthecode.timeisnow.R;
import com.beyondthecode.timeisnow.data.alarmdatabase.AlarmSource;
import com.beyondthecode.timeisnow.data.alarmservice.AlarmManager;
import com.beyondthecode.timeisnow.data.viewmodel.Alarm;
import com.beyondthecode.timeisnow.usecase.DismissAlarm;
import com.beyondthecode.timeisnow.usecase.GetAlarm;
import com.beyondthecode.timeisnow.usecase.StartAlarm;
import com.beyondthecode.timeisnow.usecase.UpdateOrCreateAlarm;
import com.beyondthecode.timeisnow.util.BaseSchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class AlarmReceiverPresenter implements AlarmReceiverContract.Presenter {

    private final DismissAlarm dismissAlarm;
    private final StartAlarm startAlarm;
    private final GetAlarm getAlarm;
    private final UpdateOrCreateAlarm updateOrCreateAlarm;

    private final AlarmReceiverContract.View view;
    private final BaseSchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public AlarmReceiverPresenter(AlarmReceiverContract.View view,
                                  AlarmSource alarmSource,
                                  AlarmManager alarmManager,
                                  BaseSchedulerProvider schedulerProvider){

        getAlarm = new GetAlarm(alarmSource);
        dismissAlarm = new DismissAlarm(alarmManager);
        startAlarm = new StartAlarm(alarmManager);
        updateOrCreateAlarm = new UpdateOrCreateAlarm(alarmSource);

        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();

    }


    @Override
    public void start() {
        getAlarmFromDatabase();
    }

    @Override
    public void stop() {
        compositeDisposable.clear();
    }

    /**
     * Query the Alarm Database for a Alarm which matches the given reminderId passed
     * in from the Activity's extras.
     */
    private void getAlarmFromDatabase(){
        Alarm alarm = view.getViewModel();

        compositeDisposable.add(
                getAlarm.runUseCase(alarm.getAlarmId())
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableSubscriber<Alarm>() {
                            @Override
                            public void onNext(Alarm alarm) {
                                checkAlarmState(alarm);
                            }

                            @Override
                            public void onError(Throwable t) {
                                view.makeToast(R.string.error_database_connection_failure);
                                view.finishActivity();
                            }

                            @Override
                            public void onComplete() {

                            }
                        })
        );
    }

    /**
     * Checks whether the Alarm should be written as INACTIVE or left alone, based on
     * alarm.isRenewAutomatically
     *
     * @param alarm
     */
    private void checkAlarmState(final Alarm alarm){
        if(alarm.isRenewAutomatically()){
            startAlarm(alarm);
        }else{
            alarm.setActive(false);

            compositeDisposable.add(
                    updateOrCreateAlarm.runUseCase(alarm)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.ui())
                                .subscribeWith(new DisposableCompletableObserver() {
                                    @Override
                                    public void onComplete() {
                                        startAlarm(alarm);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        view.makeToast(R.string.error_database_write_failure);
                                    }
                                })
            );
        }
    }

    private void startAlarm(Alarm alarm){
        compositeDisposable.add(
                startAlarm.runUseCase(alarm)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );
    }

    @Override
    public void onAlarmDismissClick() {

        compositeDisposable.add(
                dismissAlarm.runUseCase()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                view.finishActivity();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );
    }
}
