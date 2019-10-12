package com.example.assignment1.Model.source.local;

import androidx.annotation.NonNull;

import com.example.assignment1.Model.Repo;
import com.example.assignment1.Model.source.ReposDataSource;
import com.example.assignment1.utils.AppExecutors;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Concrete implementation of a data source as a db.
 * 数据源作为db的具体实现。
 */
public class ReposLocalDataSource implements ReposDataSource {

    private static volatile ReposLocalDataSource INSTANCE;

    private AppExecutors mAppExecutors;

    private ReposDao mReposDao;


    // Prevent direct instantiation.
    // 防止直接实例化。
    private ReposLocalDataSource(@NonNull AppExecutors appExecutors, @NonNull ReposDao reposDao) {
        mAppExecutors = appExecutors;
        mReposDao = reposDao;
    }

    public static ReposLocalDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull ReposDao tasksDao) {
        if (INSTANCE == null) {
            synchronized (ReposLocalDataSource.class) {
                if (INSTANCE == null)
                    INSTANCE = new ReposLocalDataSource(appExecutors, tasksDao);
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
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Repo> repos = mReposDao.getRepos();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (repos.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            // 如果表是新表或只是空表，则将调用它。
                            callback.onDataNotAvailable();
                        } else {
                            callback.onReposLoaded(repos);
                        }
                    }
                });

            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void refreshRepos() {
        // Not required because the {@link ReposRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
        // 不需要，因为{@link ReposRepository}处理从所有可用数据源刷新任务的逻辑。
    }

    @Override
    public void deleteAllRepos() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mReposDao.deleteRepos();
            }
        };
        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void sortByName() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Repo> cur = mReposDao.getRepos();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Collections.sort(cur, new Comparator<Repo>() {
                            @Override
                            public int compare(Repo o1, Repo o2) {
                                String name1 = o1.getName();
                                String name2 = o2.getName();
                                return  name1.compareTo(name2);
                            }
                        });
                        mReposDao.deleteRepos();
                        mReposDao.addRepos(cur);
                    }
                });

            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void sortByStar() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Repo> cur = mReposDao.getRepos();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Collections.sort(cur, new Comparator<Repo>() {
                            @Override
                            public int compare(Repo o1, Repo o2) {
                                int star1 = o1.getStars();
                                int star2 = o2.getStars();
                                return star1 - star2;
                            }
                        });
                        mReposDao.deleteRepos();
                        mReposDao.addRepos(cur);
                    }
                });

            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }
}

