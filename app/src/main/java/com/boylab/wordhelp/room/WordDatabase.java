package com.boylab.wordhelp.room;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.boylab.wordhelp.model.Unit;
import com.boylab.wordhelp.model.UnitDao;
import com.boylab.wordhelp.model.Word;
import com.boylab.wordhelp.model.WordDao;

/**
 * Author pengle on 2019/7/24 13:03
 * Email  pengle609@163.com
 */

@Database(entities = { Unit.class, Word.class}, version = 1,exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {

    private static final String DB_NAME = "WordDatabase.db";
    private static volatile WordDatabase instance;

    public static synchronized WordDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static WordDatabase create(final Context context) {
        return Room.databaseBuilder(context, WordDatabase.class, DB_NAME).build();
    }

    public abstract UnitDao unitDao();

    public abstract WordDao wordDao();
}