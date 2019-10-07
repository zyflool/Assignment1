package com.example.assignment1.Model.source.local;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.example.assignment1.Model.Repo;
import com.example.assignment1.Model.source.ReposDataSource;

import java.util.List;


/**
 * Concrete implementation of a data source as a db.
 * 数据源作为db的具体实现。
 */
public class ReposLocalDataSource implements ReposDataSource {

    private static volatile ReposLocalDataSource INSTANCE;

    private ReposDao mReposDao;


    // Prevent direct instantiation.
    // 防止直接实例化。
    private ReposLocalDataSource(@NonNull ReposDao reposDao) {
        mReposDao = reposDao;
    }

    public static ReposLocalDataSource getInstance(@NonNull ReposDao tasksDao) {
        if (INSTANCE == null) {
            synchronized (ReposLocalDataSource.class) {
                if (INSTANCE == null)
                    INSTANCE = new ReposLocalDataSource(tasksDao);
            }
        }
        return INSTANCE;
    }

    /**
     * Note: {@link LoadReposCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     * 注意：如果数据库不存在或表为空，则会触发......。
     */
    @Override
    public void getRepos(@NonNull final LoadReposCallback callback) {
        final List<Repo> repos = mReposDao.getRepos();
        if (repos.isEmpty()) {
            // This will be called if the table is new or just empty.
            // 如果表是新表或只是空表，则将调用它。
            callback.onDataNotAvailable();
        } else {
            callback.onReposLoaded(repos);
        }

    }

    @Override
    public void refreshRepos() {
        // Not required because the {@link ReposRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
        // 不需要，因为{@link ReposRepository}处理从所有可用数据源刷新任务的逻辑。
    }

    @Override
    public void deleteAllRepos() {
        mReposDao.deleteRepos();
    }


    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
