package com.example.assignment1.Model;

import com.example.assignment1.utils.RepositoryService;
import com.example.assignment1.utils.RetrofitWrapper;

import java.util.ArrayList;
import java.util.List;

import org.litepal.LitePal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RepoRepository {

    private static RepoRepository INSTANCE = null;

    List<Repo> repos = new ArrayList<>();

    public static RepoRepository getINSTANCE() {
        if ( INSTANCE == null )
            INSTANCE = new RepoRepository();
        return INSTANCE;
    }

    public static void destroyInstance () {
        INSTANCE = null;
    }

    public List<Repo> getRepos ( ) {
        repos = LitePal.findAll(Repo.class);
        if ( !repos.isEmpty() ) {
            return repos;
        } else {
            getReposFromRemote(repos);
        }
    }

    public void getReposFromRemote(List<Repo> repos) {

    }

    private List<RepoBean> request (List<RepoBean> repoBeans ) {
        final List<RepoBean> temp = new ArrayList<>();
        RepositoryService repositoryService = RetrofitWrapper.getInstance().create(RepositoryService.class);
        Call<List<RepoBean>> call = repositoryService.getRepos();
        call.enqueue(new Callback<List<RepoBean>>() {
            @Override
            public void onResponse(Call<List<RepoBean>> call, Response<List<RepoBean>> response) {
                if (response.isSuccessful()) {
                    response.body();
                } else {

                }
            }
            @Override
            public void onFailure(Call<List<RepoBean>> call, Throwable t) {

            }
        });
    }
}
