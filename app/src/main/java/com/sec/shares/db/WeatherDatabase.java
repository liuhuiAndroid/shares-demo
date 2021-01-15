package com.sec.shares.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sec.shares.BuildConfig;

@Database(entities = {GpBean.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

    private static volatile WeatherDatabase INSTANCE;

    public static WeatherDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (WeatherDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(),
                                    WeatherDatabase.class, BuildConfig.DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract GbDao gbDao();
}
