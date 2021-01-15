package com.sec.shares.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GbDao {

    @Insert
    void insertAll(List<GpBean> cityList);

    @Query("select * FROM gp")
    LiveData<List<GpBean>> queryAll();

    @Query("select * FROM gp where focus == 1")
    LiveData<List<GpBean>> queryFocus();

    @Query("update gp set focus=1 where id=:cityId")
    void collect(int cityId);

}
