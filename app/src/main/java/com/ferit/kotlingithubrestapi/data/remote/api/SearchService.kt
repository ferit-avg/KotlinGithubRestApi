package com.ferit.kotlingithubrestapi.data.remote.api

import com.ferit.kotlingithubrestapi.data.model.repo.Repo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {
    @GET("users/{username}/repos")
    suspend fun getUserAllRepos(
        @Path("username") username: String,
        @Query("page") page: Int
    ): List<Repo>
}