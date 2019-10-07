package com.example.assignment1.Model.source;

import static com.google.common.base.Preconditions.checkNotNull;

import androidx.annotation.NonNull;

import com.example.assignment1.Model.Repo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * 将数据源中的任务加载到缓存中的具体实现。
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 * 为简单起见，仅当本地数据库不存在或为空时，才使用远程数据源在本地持久化数据和从服务器获取的数据之间实现哑同步。
 */
public class ReposRepository implements ReposDataSource {

    private static ReposRepository INSTANCE = null;

    private final ReposDataSource mReposRemoteDataSource;

    private final ReposDataSource mReposLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     * 此变量具有程序包本地可见性，因此可以从测试中访问它。
     */
    Map<String, Repo> mCachedRepos;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     * 将缓存标记为无效，以在下次请求数据时强制进行更新。此变量具有程序包本地可见性，因此可以从测试中访问它。
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    //防止直接实例化。
    private ReposRepository (@NonNull ReposDataSource reposRemoteDataSource, @NonNull ReposDataSource reposLocalDataSource) {
        mReposRemoteDataSource = checkNotNull(reposRemoteDataSource);
        mReposLocalDataSource = checkNotNull(reposLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     * 返回此类的单个实例，并在必要时创建它。
     *
     * @param reposRemoteDataSource the backend data source  后端数据源
     * @param reposLocalDataSource  the device storage data source 设备存储数据源
     * @return the {@link ReposDataSource} instance
     */
    public static ReposRepository getInstance(ReposDataSource reposRemoteDataSource, ReposDataSource reposLocalDataSource) {
        if (INSTANCE == null)
            INSTANCE = new ReposRepository(reposRemoteDataSource, reposLocalDataSource);
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(ReposDataSource, ReposDataSource)} to create a new instance
     * next time it's called.
     * 用于强制......在下次调用该实例时创建一个新实例。
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets tasks from cache, local data source (SQLite) or remote data source, whichever is available first.
     * 从缓存，本地数据源（SQLite）或远程数据源（以先到者为准）获取任务。
     * <p>
     * Note: {@link LoadReposCallback#onDataNotAvailable()} is fired if all data sources fail to get the data.
     * 如果所有数据源均无法获取数据，则会触发......。
     */
    @Override
    public void getRepos(@NonNull final LoadReposCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        //如果可用且不脏，立即用缓存响应
        if (mCachedRepos != null && !mCacheIsDirty) {
            callback.onReposLoaded(new ArrayList<>(mCachedRepos.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            //如果缓存脏了，我们需要从网络中获取新数据
            getReposFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            //查询本地存储（如果有）。如果不是，请查询网络。
            mReposLocalDataSource.getRepos(new LoadReposCallback() {
                @Override
                public void onReposLoaded(List<Repo> repos) {
                    refreshCache(repos);
                    callback.onReposLoaded(new ArrayList<>(mCachedRepos.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getReposFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void refreshRepos() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllRepos() {
        mReposRemoteDataSource.deleteAllRepos();
        mReposLocalDataSource.deleteAllRepos();

        if (mCachedRepos == null) {
            mCachedRepos = new LinkedHashMap<>();
        }
        mCachedRepos.clear();
    }

    private void getReposFromRemoteDataSource(@NonNull final LoadReposCallback callback) {
        mReposRemoteDataSource.getRepos(new LoadReposCallback() {
            @Override
            public void onReposLoaded(List<Repo> repos) {
                refreshCache(repos);
                callback.onReposLoaded(new ArrayList<>(mCachedRepos.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Repo> repos) {
        if (mCachedRepos == null) {
            mCachedRepos = new LinkedHashMap<>();
        }
        mCachedRepos.clear();
        for ( int i = 0 ; i < repos.size() ; i++ ) {
            mCachedRepos.put(i+"", repos.get(i));
        }
        mCacheIsDirty = false;
    }

}
