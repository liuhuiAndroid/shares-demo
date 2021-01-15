package com.sec.shares.network;

import com.sec.shares.db.GpBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaseApi {

    @GET("base/gplist")
    Observable<List<GpBean>> baseGplist(@Query(value = "licence") String licence);

}
