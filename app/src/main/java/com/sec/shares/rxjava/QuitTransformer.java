package com.sec.shares.rxjava;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public final class QuitTransformer<T> implements ObservableTransformer<T, T>,
        FlowableTransformer<T, T> {

    private final Observable<?> mObservable;

    public QuitTransformer(Observable<?> observable) {
        mObservable = observable;
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.takeUntil(mObservable.toFlowable(BackpressureStrategy.LATEST));
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.takeUntil(mObservable);
    }
}
