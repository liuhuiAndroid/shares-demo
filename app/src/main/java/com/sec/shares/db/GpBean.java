package com.sec.shares.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * {"dm":"000001","mc":"平安银行","jys":"sz"}
 */
@Entity(tableName = "gp")
public class GpBean implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "dm")
    private String dm;

    @ColumnInfo(name = "mc")
    private String mc;

    @ColumnInfo(name = "jys")
    private String jys;

    @ColumnInfo(name = "focus")
    private Integer focus;

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer focus) {
        this.focus = focus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getJys() {
        return jys;
    }

    public void setJys(String jys) {
        this.jys = jys;
    }
}
