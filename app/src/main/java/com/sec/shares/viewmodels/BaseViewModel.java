package com.sec.shares.viewmodels;

import androidx.lifecycle.ViewModel;

import com.sec.shares.annotation.QuitEvent;
import com.sec.shares.rxjava.QuitTransformer;
import com.sec.shares.rxjava.RespContentTransformer;
import com.sec.shares.rxjava.ResponseTransformer;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class BaseViewModel extends ViewModel {

    private final BehaviorSubject<Integer> mBehaviorSubject = BehaviorSubject.create();

    /**
     * 线程调度
     */
    protected <T> ObservableTransformer<T, T> applySchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread());
    }

    /**
     * 处理响应
     */
    protected final <T> ResponseTransformer<T> applyResponse() {
        return new ResponseTransformer<>(bindToCleared());
    }

    /**
     * 直接处理响应内容
     */
    protected final <T> RespContentTransformer<T> applyRespContent() {
        return new RespContentTransformer<>(bindToCleared());
    }

    /**
     * 绑定到{@link #onCleared()}
     */
    protected <T> QuitTransformer<T> bindToCleared() {
        return new QuitTransformer<>(mBehaviorSubject.filter(quitEvent -> quitEvent == QuitEvent.QUIT));
    }

    /**
     * 绑定到{@link #onCleared()}
     */
    protected <T> QuitTransformer<T> bindToCleared(int event) {
        return new QuitTransformer<>(mBehaviorSubject.filter(quitEvent -> quitEvent == event));
    }

    protected final void unSubscribe() {
        mBehaviorSubject.onNext(QuitEvent.QUIT);
    }

    protected final void unSubscribe(int event) {
        mBehaviorSubject.onNext(event);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mBehaviorSubject.onNext(QuitEvent.QUIT);
    }
}
