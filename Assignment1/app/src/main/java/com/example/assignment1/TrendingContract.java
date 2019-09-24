package com.example.assignment1;


import com.example.assignment1.Model.Repo;
import com.example.assignment1.base.BasePresenter;
import com.example.assignment1.base.BaseView;

import java.util.List;

public interface TrendingContract {

    interface View extends BaseView<Presenter> {

        void showRepos(List<Repo> repos);

        void showLoadingReposError();

        void showLoadingProgress();


    }


    interface Presenter extends BasePresenter {

        void sortByName();

        void sortByStar();

        void loadRepos();



    }

}
