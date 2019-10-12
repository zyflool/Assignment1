package com.example.assignment1.Model.source.remote;

import androidx.annotation.NonNull;

import com.example.assignment1.Model.Repo;
import com.example.assignment1.Model.RepoBean;
import com.example.assignment1.Model.source.ReposDataSource;
import com.example.assignment1.Model.source.local.ReposDao;
import com.example.assignment1.utils.AppExecutors;
import com.example.assignment1.utils.RepositoryService;
import com.example.assignment1.utils.RetrofitWrapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of the data source that adds a latency simulating network.
 * 数据源的实现增加了一个延迟模拟网络。
 */
public class ReposRemoteDataSource implements ReposDataSource {

    private static ReposRemoteDataSource INSTANCE;

    private ReposDao mReposDao;

    private AppExecutors mAppExecutors;

    public static ReposRemoteDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull ReposDao reposDao) {
        if (INSTANCE == null)
            INSTANCE = new ReposRemoteDataSource(appExecutors, reposDao);
        return INSTANCE;
    }

    // Prevent direct instantiation.
    // 防止直接实例化。
    private ReposRemoteDataSource(@NonNull AppExecutors appExecutors, @NonNull ReposDao reposDao) {
        mReposDao = reposDao;
        mAppExecutors = appExecutors;
    }

    /**
     * Note: {@link LoadReposCallback#onDataNotAvailable()} is never fired. In a real remote data
     * source implementation, this would be fired if the server can't be contacted or the server
     * returns an error.
     * 注意：永远不会触发.......。
     * 在真正的远程数据源实现中，如果无法联系服务器或服务器返回错误，则会触发此事件。
     */
    @Override
    public void getRepos(final @NonNull LoadReposCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Repo> repos = new ArrayList<>();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        RepositoryService repositoryService = RetrofitWrapper.getInstance().create(RepositoryService.class);
                        Call<List<RepoBean>> call = repositoryService.getRepos();
                        call.enqueue(new Callback<List<RepoBean>>() {
                            @Override
                            public void onResponse(Call<List<RepoBean>> call, Response<List<RepoBean>> response) {
                                if (response.isSuccessful()) {
                                    List<RepoBean> repoBeans = response.body();
                                    int n = repoBeans.size();
                                    for ( int i = 0 ; i < n ; i++ ) {
                                        RepoBean temp = repoBeans.get(i);
                                        repos.add(new Repo(temp.getAuthor(), temp.getAvatar(), temp.getName(),
                                                temp.getUrl(), temp.getDescription(), temp.getLanguage(),
                                                temp.getLanguageColor(), temp.getStars(), temp.getForks()));
                                    }
                                    mReposDao.deleteRepos();
                                    mReposDao.addRepos(repos);
                                    callback.onReposLoaded(repos);
                                } else {
                                    callback.onDataNotAvailable();
                                }
                            }
                            @Override
                            public void onFailure(Call<List<RepoBean>> call, Throwable t) {
                                callback.onDataNotAvailable();
                            }
                        });
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
        //Not required
    }

    @Override
    public void sortByName() {

    }

    @Override
    public void sortByStar() {

    }
}