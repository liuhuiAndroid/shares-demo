package com.sec.shares.network;

import com.sec.shares.entity.F10InfoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TimeApi {

    @GET("time/f10/info/{id}")
    Observable<F10InfoBean> f10Info(@Path(value = "id") String id,
                                    @Query(value = "licence") String licence);

}