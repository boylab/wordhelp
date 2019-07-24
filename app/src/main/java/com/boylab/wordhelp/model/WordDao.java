package com.boylab.wordhelp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Author pengle on 2019/7/24 13:48
 * Email  pengle609@163.com
 */
@Dao
public interface WordDao {

    @Query("SELECT * FROM word WHERE word.unit = :unitId ORDER BY word.id")
    List<Word> getAllWords(long unitId);

    @Insert
    void insert(Word... words);

    @Update
    void update(Word... words);

    @Delete
    void delete(Word... words);
}
