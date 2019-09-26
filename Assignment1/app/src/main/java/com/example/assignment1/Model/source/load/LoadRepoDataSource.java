package com.example.assignment1.Model.source.load;

import com.example.assignment1.Model.Repo;
import com.example.assignment1.utils.RepositoryService;
import com.example.assignment1.utils.RetrofitWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadRepoDataSource {


    private void request() {
        RepositoryService repositoryService = RetrofitWrapper.getInstance().create(RepositoryService.class);
        Call<List<Repo>> call = repositoryService.getRepos();
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }
            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    }

}
