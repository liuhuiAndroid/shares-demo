package com.sec.shares.network;

import android.content.Context;

import com.sec.shares.BuildConfig;
import com.sec.shares.MyApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public final class ApiCreate {

    private Retrofit.Builder mBuilder;

    private ApiCreate(Context context, boolean debug) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(BuildConfig.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(BuildConfig.READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .cache(new Cache(context.getCacheDir(), 100 * 1024 * 1024L));

        if (debug) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message ->
                    Timber.tag("OKHttp-----").i(message)
            );
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }

        mBuilder = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());
    }

    /**
     * 创建
     */
    public static <T> T create(Class<T> clazz) {
        return SingletonHolder.INSTANCE.mBuilder.build().create(clazz);
    }

    private static class SingletonHolder {
        private final static ApiCreate INSTANCE = new ApiCreate(MyApplication.mContext, BuildConfig.DEBUG);
    }
}