package com.example.assignment1.utils;

import com.example.assignment1.Model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RepositoryService {
    @GET("/repositories")
    Call<List<Repo>> getRepos();

}
