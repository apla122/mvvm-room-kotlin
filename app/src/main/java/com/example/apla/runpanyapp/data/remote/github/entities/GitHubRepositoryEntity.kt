package com.example.apla.runpanyapp.data.remote.github.entities

data class GitHubRepositoryEntity(
        val id: Int = 0,
        val name: String = "",
        val full_name: String = "",
        val owner_id: String = "",
        val html_url: String? = null,
        val description: String? = "",
        val fork: Boolean = false,
        val stargazers_count: Int = 0,
        val watchers_count: Int = 0,
        val language: String? = null,
        val forks_count: Int = 0)