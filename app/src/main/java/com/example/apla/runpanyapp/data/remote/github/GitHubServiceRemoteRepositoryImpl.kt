package com.example.apla.runpanyapp.data.remote.github

import com.example.apla.runpanyapp.data.remote.ApiClient
import com.example.apla.runpanyapp.data.remote.github.entities.GitHubRepositoryEntity
import com.example.apla.runpanyapp.data.remote.github.entities.GitHubUserEntity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class GitHubServiceRemoteRepositoryImpl : GitHubServiceRemoteRepository {

    override fun getUser(userName: String): Single<GitHubUserEntity> {
        return ApiClient.github
                .getUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
    }

    fun getRepositories(userName: String, page: Int): Observable<List<GitHubRepositoryEntity>> {
        return ApiClient.github
                .getRepositories(userName = userName, page = page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
    }

    override fun getRepositories(userName: String, page: Int, perPage: Int): Observable<List<GitHubRepositoryEntity>> {
        return ApiClient.github
                .getRepositories(userName = userName, page = page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
    }

}