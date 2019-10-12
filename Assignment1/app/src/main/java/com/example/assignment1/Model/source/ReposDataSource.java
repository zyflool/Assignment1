package com.example.assignment1.Model.source;

import androidx.annotation.NonNull;

import com.example.assignment1.Model.Repo;

import java.util.List;

/**
 * Main entry point for accessing tasks data.
 * 访问任务数据的主要入口点。
 * <p>
 * For simplicity, only getTasks() and getTask() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 * 为简单起见，只有getTasks（）和getTask（）具有回调。考虑将回调添加到其他方法，以通知用户网络/数据库错误或成功的操作。
 * For example, when a new task is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 * 例如，创建新任务时，它会同步存储在缓存中，但通常数据库或网络上的每个操作都应在不同的线程中执行。
 */

public interface ReposDataSource {

    interface LoadReposCallback {

        void onReposLoaded(List<Repo> repos);

        void onDataNotAvailable();
    }

    void getRepos(@NonNull LoadReposCallback callback);

    void refreshRepos();

    void deleteAllRepos();

    void sortByName();

    void sortByStar();
}