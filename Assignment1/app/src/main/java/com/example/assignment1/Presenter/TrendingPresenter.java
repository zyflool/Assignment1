package com.example.assignment1.Presenter;


import androidx.annotation.NonNull;

import com.example.assignment1.Model.Repo;
import com.example.assignment1.Model.source.ReposDataSource;
import com.example.assignment1.Model.source.ReposRepository;
import com.example.assignment1.TrendingContract;
import com.example.assignment1.View.TrendingFragment;


import java.util.ArrayList;
import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link TrendingFragment}), retrieves the data and updates the
 * UI as required.
 * 从UI（{@link TrendingFragment}）侦听用户操作，检索数据并根据需要更新UI。
 */
public class TrendingPresenter implements TrendingContract.Presenter {

    private final ReposRepository mReposRepository;

    private final TrendingContract.View mTrendingView;

    private boolean mFirstLoad = true;

    public TrendingPresenter (@NonNull ReposRepository repoRepository, @NonNull TrendingContract.View trendingView) {
        mReposRepository = checkNotNull(repoRepository, "repoRepository cannot be null");
        mTrendingView = checkNotNull(trendingView, "trendingView cannot be null!");

        mTrendingView.setPresenter(this);
    }


    @Override
    public void start() {
        loadRepos(false);
    }


    @Override
    public void sortByName() {
    }

    @Override
    public void sortByStar() {

    }

    @Override
    public void loadRepos(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        // 简化示例：首次加载时将强制进行网络重新加载。
        loadRepos(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }


    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link ReposDataSource}
     *                      传入true以刷新{@link ReposDataSource}中的数据
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     *                      传递true以在UI中显示加载图标
     */
    private void loadRepos(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mTrendingView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            mReposRepository.refreshRepos();
        }

        mReposRepository.getRepos(new ReposDataSource.LoadReposCallback() {
            @Override
            public void onReposLoaded(List<Repo> repos) {
                List<Repo> tasksToShow = new ArrayList<Repo>();

                // The view may not be able to handle UI updates anymore
                if (!mTrendingView.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mTrendingView.setLoadingIndicator(false);
                }

                processRepos(tasksToShow);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mTrendingView.isActive()) {
                    return;
                }
                mTrendingView.showNoInternetConnection();
            }
        });
    }

    private void processRepos(List<Repo> repos) {
        mTrendingView.showRepos(repos);
    }


}
