package com.example.assignment1;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.assignment1.Model.source.ReposRepository;
import com.example.assignment1.Model.source.local.RepoDatabase;
import com.example.assignment1.Model.source.local.ReposLocalDataSource;
import com.example.assignment1.Model.source.remote.ReposRemoteDataSource;
import com.example.assignment1.utils.AppExecutors;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injection {

    public static ReposRepository provideReposRepository(@NonNull Context context) {
        checkNotNull(context);
        RepoDatabase database = RepoDatabase.getInstance(context);
        return ReposRepository.getInstance(ReposRemoteDataSource.getInstance(new AppExecutors(), database.repoDao()),
                ReposLocalDataSource.getInstance(new AppExecutors(), database.repoDao()));
    }
}