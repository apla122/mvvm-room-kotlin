package com.example.apla.runpanyapp.data.remote

import com.example.apla.runpanyapp.data.remote.github.GitHubServiceRemoteRepository

/**
 * API Client
 */
object ApiClient {

    val github: GitHubServiceRemoteRepository
        get() {
            val endPoint = "https://api.github.com/"
            return RetrofitClient
                    .default(endPoint)
                    .build()
                    .create(GitHubServiceRemoteRepository::class.java)
        }
}