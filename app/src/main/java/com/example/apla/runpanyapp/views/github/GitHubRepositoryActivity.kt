package com.example.apla.runpanyapp.views.github

import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.databinding.GithubRepositoryActivityBinding
import com.example.apla.runpanyapp.models.GitHubRepositoryModel
import com.example.apla.runpanyapp.viewmodels.github.GitHubRepositoryViewModel
import com.example.apla.runpanyapp.views.BaseActivity


class GitHubRepositoryActivity : BaseActivity() {

    private var binding: GithubRepositoryActivityBinding? = null
    private var mViewModel: GitHubRepositoryViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.github_repository_activity)
        setSupportActionBar(binding!!.toolbar)
        val actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val bundle = intent.extras
        val repository = bundle.getParcelable(EXTRA_REPOSITORY) as GitHubRepositoryModel

        // ViewModel
        //DI
        val factory = GitHubRepositoryViewModel.Factory(
                application,
                repository
        )
        //ViewModelインスタンスを取得
        mViewModel = ViewModelProviders.of(this, factory).get(GitHubRepositoryViewModel::class.java)
//        mViewModel = GitHubRepositoryViewModel(application, repository)
        binding!!.viewModel = mViewModel

        //Currently there is no way of setting an activity title using data binding
        title = repository.name

    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel!!.destroy()
    }

    companion object {

        private val EXTRA_REPOSITORY = "EXTRA_REPOSITORY"

        fun newIntent(context: Context, repository: GitHubRepositoryModel): Intent {
            val intent = Intent(context, GitHubRepositoryActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_REPOSITORY, repository)
            intent.putExtras(bundle)
            return intent
        }
    }

}