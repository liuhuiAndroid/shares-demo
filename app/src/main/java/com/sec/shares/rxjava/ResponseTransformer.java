package com.sec.shares.rxjava;

import com.sec.shares.app.ApiException;
import com.sec.shares.entity.Response;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class ResponseTransformer<T> implements ObservableTransformer<Response<T>,
        Response<T>> {

    private final QuitTransformer<Response<T>> mQuitTransformer;

    public ResponseTransformer(QuitTransformer<Response<T>> quitTransformer) {
        mQuitTransformer = quitTransformer;
    }

    @Override
    public ObservableSource<Response<T>> apply(Observable<Response<T>> upstream) {
        return upstream.map(response -> {
            if (ApiException.NET_SUCCESS != response.getCode() &&
                    ApiException.EMPTY != response.getCode()) {
                throw new ApiException(response.getMessage(), response.getCode());
            }
            return response;
        }).compose(mQuitTransformer);
    }
}
