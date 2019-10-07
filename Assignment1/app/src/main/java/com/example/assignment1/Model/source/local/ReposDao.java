package com.example.assignment1.Model.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.assignment1.Model.Repo;

import java.util.List;

/**
 * Data Access Object for the repos table.
 * repos表的数据访问对象。
 */
@Dao
public interface ReposDao {
    /**
     * Select all tasks from the repos table.
     *
     * @return all repos.
     */
    @Query("SELECT * FROM Repos")
    List<Repo> getRepos();

    /**
     * Delete all repos.
     */
    @Query("DELETE FROM Repos")
    void deleteRepos();

    /**
     * Add all latest Repos.
     *
     * @param repos all latest repos.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addRepos(List<Repo> repos);

}

