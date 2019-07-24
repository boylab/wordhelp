package com.boylab.wordhelp;

import android.app.Application;

/**
 * Author pengle on 2019/7/24 14:27
 * Email  pengle609@163.com
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /*MyApp.database =  Room.databaseBuilder(this, AppDatabase::class.java, "MyDatabase").build()*/
    }
}
