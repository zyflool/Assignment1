package com.example.assignment1.Presenter;


import androidx.annotation.NonNull;

import com.example.assignment1.Model.Repo;
import com.example.assignment1.Model.RepoRepository;
import com.example.assignment1.TrendingContract;


import java.util.ArrayList;
import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;

public class TrendingPresenter implements TrendingContract.Presenter {

    private final RepoRepository mRepoRepository;

    private final TrendingContract.View mTrendingView;

    private boolean mFirstLoad = true;


    public TrendingPresenter (@NonNull RepoRepository repoRepository, @NonNull TrendingContract.View trendingView) {
        mRepoRepository = checkNotNull(repoRepository, "repoRepository cannot be null");
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
        loadRepos(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }


    private void loadRepos(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mTrendingView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            mRepoRepository.refreshTasks();
        }

        mRepoRepository.getRepos(new ReposDataSource.LoadReposCallback() {
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
