package com.boylab.wordhelp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.IdentityHashMap;

/**
 * Author pengle on 2019/7/24 13:48
 * Email  pengle609@163.com
 */
@Entity(primaryKeys = {"id","word"})
public class Word {
    //@PrimaryKey(autoGenerate = true)
    private long id;

    private long unit;
    @NonNull
    private String word;
    private int studytimes;
    private long updatetime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUnit() {
        return unit;
    }

    public void setUnit(long unit) {
        this.unit = unit;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getStudytimes() {
        return studytimes;
    }

    public void setStudytimes(int studytimes) {
        this.studytimes = studytimes;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }
}
