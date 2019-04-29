package com.example.apla.runpanyapp.data

import com.example.apla.runpanyapp.data.remote.github.GitHubServiceRemoteRepositoryImpl
import com.example.apla.runpanyapp.data.remote.github.entities.GitHubRepositoryEntity
import com.example.apla.runpanyapp.data.remote.github.entities.GitHubUserEntity
import io.reactivex.Observable
import io.reactivex.Single

open class GitHubServiceRepository {

    open fun requestUser(id: String): Single<GitHubUserEntity> {
        return GitHubServiceRemoteRepositoryImpl().getUser(id)
    }

    open fun requestRepository(userName: String, page: Int): Observable<List<GitHubRepositoryEntity>> {
        return GitHubServiceRemoteRepositoryImpl().getRepositories(userName, page)
    }
}