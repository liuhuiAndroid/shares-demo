package com.sec.shares.rxjava;

import com.sec.shares.app.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ResponseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        success(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            error((ApiException) e);
        } else {
            error(new ApiException(ApiException.NET_ERROR));
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void success(T t);

    public abstract void error(ApiException apiException);
}
