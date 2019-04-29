package com.example.apla.runpanyapp.viewmodels.github

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import android.content.Intent
import android.view.View;
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.models.GitHubRepositoryModel
import com.example.apla.runpanyapp.viewmodels.BaseViewModel
import com.example.apla.runpanyapp.views.github.GitHubRepositoryActivity

/**
 * View model for each item in the getRepositories RecyclerView
 */
class GitHubRepositoryDetailViewModel(app: Application, repository: GitHubRepositoryModel) : BaseViewModel(app) {

    private var repository: GitHubRepositoryModel = repository
    private var context: Context = app.applicationContext

    init {
    }

    fun getName(): String {
        return repository!!.name!!
    }

    fun getDescription(): String {
        return  if (repository!!.description != null) repository!!.description!! else ""
    }

    fun getStars(): String {
        return context.getString(R.string.text_stars, repository!!.stars)
    }

    fun getWatchers(): String {
        return context.getString(R.string.text_watchers, repository!!.watchers)
    }

    fun getForks(): String {
        return context.getString(R.string.text_forks, repository!!.forks)
    }

    fun onItemClick(view: View) {
        val intent = GitHubRepositoryActivity.newIntent(context, repository!!)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    // Allows recycling ItemRepoViewModels within the recyclerview adapter
    fun setRepository(repository: GitHubRepositoryModel) {
        this.repository = repository
        notifyChange()
    }

    override fun destroy() {
        //In this case destroy doesn't need to do anything because there is not async calls
    }

    class Factory(private val application: Application, private val repository: GitHubRepositoryModel) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return GitHubRepositoryDetailViewModel(application, repository) as T
        }
    }

}
