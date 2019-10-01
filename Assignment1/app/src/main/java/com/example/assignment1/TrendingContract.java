package com.example.assignment1;


import com.example.assignment1.Model.Repo;
import com.example.assignment1.base.BasePresenter;
import com.example.assignment1.base.BaseView;

import java.util.List;

public interface TrendingContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showRepos(List<Repo> repos);

        boolean isActive();

        void showNoInternetConnection();


    }


    interface Presenter extends BasePresenter {

        void sortByName();

        void sortByStar();

        void loadRepos(boolean forceUpdate);



    }

}
