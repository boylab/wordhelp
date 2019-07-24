package com.boylab.wordhelp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Author pengle on 2019/7/24 13:11
 * Email  pengle609@163.com
 */
@Dao
public interface UnitDao {

    @Query("SELECT * FROM unit ORDER BY unit.id")
    List<Unit> getAllUnit();

    @Insert
    void insert(Unit... units);

    @Update
    void update(Unit... units);

    @Delete
    void delete(Unit... units);

}
