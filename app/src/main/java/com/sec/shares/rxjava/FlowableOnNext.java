package com.sec.shares.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class FlowableOnNext<T> implements Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s) {
        s.request(128);
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
