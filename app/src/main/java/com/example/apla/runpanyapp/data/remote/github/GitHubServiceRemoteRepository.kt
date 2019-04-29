package com.example.apla.runpanyapp.data.remote.github

import com.example.apla.runpanyapp.data.remote.github.entities.GitHubRepositoryEntity
import com.example.apla.runpanyapp.data.remote.github.entities.GitHubUserEntity
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubServiceRemoteRepository {

    @GET("users/{userName}")
    fun getUser(@Path("userName") userName: String): Single<GitHubUserEntity>

    @GET("users/{userName}/repos")
    fun getRepositories(@Path("userName") userName: String,
                     @Query("page") page: Int,
                     @Query("per_page") perPage: Int = 100): Observable<List<GitHubRepositoryEntity>>

}