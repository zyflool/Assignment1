package com.example.assignment1.Model.source;

import androidx.annotation.NonNull;

import com.example.assignment1.Model.Repo;

import java.util.List;

public interface ReposDataSource {

    interface LoadReposCallback {

        void onReposLoaded(List<Repo> repos);

        void onDataNotAvailable();
    }

    interface GetRepoCallback {

        void onReposLoaded(Repo repo);

        void onDataNotAvailable();
    }

    void getRepos(@NonNull LoadReposCallback callback);

    void getRepo(@NonNull String RepoId, @NonNull GetRepoCallback callback);

    void saveRepo(@NonNull Repo repo);

    void refreshTasks();

    void clearRepos();
}
