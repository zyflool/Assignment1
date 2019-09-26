package com.example.assignment1.Presenter;

import androidx.annotation.NonNull;

import com.example.assignment1.Model.source.RepoRepository;
import com.example.assignment1.TrendingContract;

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
    public void loadRepos() {

    }
}
