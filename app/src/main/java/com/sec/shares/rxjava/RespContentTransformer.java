package com.sec.shares.rxjava;

import com.sec.shares.entity.Response;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public final class RespContentTransformer<T> implements ObservableTransformer<Response<T>, T> {

    private final QuitTransformer<T> mQuitTransformer;

    public RespContentTransformer(QuitTransformer<T> quitTransformer) {
        mQuitTransformer = quitTransformer;
    }

    @Override
    public ObservableSource<T> apply(Observable<Response<T>> upstream) {
        return upstream.map(Response::getContent).compose(mQuitTransformer);
    }
}
