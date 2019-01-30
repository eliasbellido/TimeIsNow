package com.beyondthecode.timeisnow.usecase;

import io.reactivex.Completable;

public interface UseCaseCompletable<Params> {
    Completable runUseCase(Params... params);
}
