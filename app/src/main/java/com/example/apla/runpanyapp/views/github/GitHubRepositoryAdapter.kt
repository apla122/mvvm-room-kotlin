package com.example.apla.runpanyapp.views.github

import android.app.Application
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.databinding.GithubRepositoryItemBinding

import com.example.apla.runpanyapp.models.GitHubRepositoryModel
import com.example.apla.runpanyapp.viewmodels.github.GitHubRepositoryDetailViewModel
import com.example.apla.runpanyapp.views.BaseFragment

class GitHubRepositoryAdapter(private val app: Application) : RecyclerView.Adapter<GitHubRepositoryAdapter.GitHubRepositoryViewHolder>() {

    private var repositories: kotlin.collections.List<GitHubRepositoryModel>? = null

    init {
        this.repositories = emptyList()
    }

    constructor(app: Application, fragment: BaseFragment, repositories: List<GitHubRepositoryModel>) : this(app) {
        this.repositories = repositories
    }

    fun setRepositories(repositories: List<GitHubRepositoryModel>) {
        this.repositories = repositories
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepositoryViewHolder {
        val binding = DataBindingUtil.inflate<GithubRepositoryItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.github_repository_item,
                parent,
                false)
        return GitHubRepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GitHubRepositoryViewHolder, position: Int) {
        holder.bindRepository(repositories!![position])
    }

    override fun getItemCount(): Int {
        return repositories!!.size
    }

    inner class GitHubRepositoryViewHolder(internal val binding: GithubRepositoryItemBinding) : RecyclerView.ViewHolder(binding.cardView) {

        internal fun bindRepository(repository: GitHubRepositoryModel) {
            if (binding.viewModel == null) {
                // ViewModel
                binding.viewModel = GitHubRepositoryDetailViewModel(app, repository)
            } else {
                binding.viewModel!!.setRepository(repository)
            }
        }
    }

}
