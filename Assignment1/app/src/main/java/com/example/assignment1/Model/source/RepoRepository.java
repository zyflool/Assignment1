package com.example.assignment1.Model.source;

import androidx.annotation.NonNull;

import com.example.assignment1.Model.Repo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.util.Preconditions.checkNotNull;

public class RepoRepository implements ReposDataSource{

    private static RepoRepository INSTANCE = null;

    private final ReposDataSource mReposLoadDataSource;

    private final ReposDataSource mReposRemoteDataSource;

    private final ReposDataSource mReposSaveDataSource;

    private final ReposDataSource mReposSortDataSource;

    Map<String, Repo> mCachedRepos;

    boolean mCacheIsDirty = false;

    private RepoRepository (@NonNull ReposDataSource reposLoadDataSource,
                            @NonNull ReposDataSource reposRemoteDataSource,
                            @NonNull ReposDataSource reposSaveDataSource,
                            @NonNull ReposDataSource reposSortDataSource) {
        mReposLoadDataSource = checkNotNull(reposLoadDataSource);
        mReposRemoteDataSource = checkNotNull(reposRemoteDataSource);
        mReposSaveDataSource = checkNotNull(reposSaveDataSource);
        mReposSortDataSource = checkNotNull(reposSortDataSource);
    }

    public static RepoRepository getINSTANCE(ReposDataSource reposLoadDataSource,
                                             ReposDataSource reposRemoteDataSource,
                                             ReposDataSource reposSaveDataSource,
                                             ReposDataSource reposSortDataSource) {
        if ( INSTANCE == null ) {
            INSTANCE = new RepoRepository(reposLoadDataSource,reposRemoteDataSource, reposSaveDataSource, reposSortDataSource);
        }

        return INSTANCE;
    }

    public static void destroyInstance () {
        INSTANCE = null;
    }

    @Override
    public void getRepos(@NonNull final LoadReposCallback callback) {
        checkNotNull(callback);

        if ( mCachedRepos != null && !mCacheIsDirty ) {
            callback.onReposLoaded(new ArrayList<Repo>(mCachedRepos.values()));
            return ;
        }

        if ( mCacheIsDirty ) {
            getReposFromeRemoteDataSource(callback);
        } else {
            mReposLoadDataSource.getRepos(new LoadReposCallback() {
                @Override
                public void onReposLoaded(List<Repo> repos) {
                    refreshRepos(repos);
                    callback.onReposLoaded(new ArrayList<Repo>(mCachedRepos.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getReposFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void getRepo(@NonNull String RepoName, @NonNull GetRepoCallback callback) {
        checkNotNull(RepoName);
        checkNotNull(callback);

        Repo cachedTask = getTaskWithId(taskId);

        // Respond immediately with cache if available
        if (cachedTask != null) {
            callback.onTaskLoaded(cachedTask);
            return;
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        mTasksLocalDataSource.getTask(taskId, new GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedTasks == null) {
                    mCachedTasks = new LinkedHashMap<>();
                }
                mCachedTasks.put(task.getId(), task);
                callback.onTaskLoaded(task);
            }

            @Override
            public void onDataNotAvailable() {
                mTasksRemoteDataSource.getTask(taskId, new GetTaskCallback() {
                    @Override
                    public void onTaskLoaded(Task task) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedTasks == null) {
                            mCachedTasks = new LinkedHashMap<>();
                        }
                        mCachedTasks.put(task.getId(), task);
                        callback.onTaskLoaded(task);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void saveRepo(@NonNull Repo repo) {
        checkNotNull(repo);
        mReposRemoteDataSource.saveRepo(repo);
        mReposLoadDataSource.saveRepo(repo);
        mReposSaveDataSource.saveRepo(repo);
        mReposSortDataSource.saveRepo(repo);

        if ( mCachedRepos == null ) {
            mCachedRepos = new LinkedHashMap<>();
        }
        mCachedRepos.put(repo.getName(), repo);
    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void clearRepos() {
        mReposRemoteDataSource.clearRepos();
        mReposLoadDataSource.clearRepos();
        mReposSortDataSource.clearRepos();
        mReposSaveDataSource.clearRepos();

        // Do in memory cache update to keep the app UI up to date
        if (mCachedRepos == null) {
            mCachedRepos = new LinkedHashMap<>();
        }

    }
}
