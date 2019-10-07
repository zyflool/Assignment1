package com.example.assignment1.Model.source.local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.assignment1.Model.Repo;

/**
 * The Room Database that contains the Repo table.
 * 包含Repo表的Room数据库。
 */
@Database(entities = {Repo.class}, version = 1)
public abstract class RepoDatabase extends RoomDatabase {

    private static RepoDatabase INSTANCE;

    public abstract ReposDao repoDao();

    private static final Object sLock = new Object();

    public static RepoDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        RepoDatabase.class, "Repos.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}