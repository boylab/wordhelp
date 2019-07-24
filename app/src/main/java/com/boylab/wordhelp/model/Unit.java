package com.boylab.wordhelp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Author pengle on 2019/7/24 12:09
 * Email  pengle609@163.com
 */
@Entity
public class Unit {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String unit;
    private long unittime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getUnittime() {
        return unittime;
    }

    public void setUnittime(long unittime) {
        this.unittime = unittime;
    }

}
