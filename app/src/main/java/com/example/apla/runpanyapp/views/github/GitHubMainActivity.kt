package com.example.apla.runpanyapp.views.github

import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.databinding.GithubMainActivityBinding
import com.example.apla.runpanyapp.models.GitHubRepositoryModel
import com.example.apla.runpanyapp.viewmodels.github.GitHubViewModel
import com.example.apla.runpanyapp.views.BaseActivity

class GitHubMainActivity : BaseActivity(), GitHubViewModel.DataListener {

    private var binding: GithubMainActivityBinding? = null
    private var mViewModel: GitHubViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.github_main_activity)

        // ViewModel
        //DI
        val factory = GitHubViewModel.Factory(application,this)
        //ViewModelインスタンスを取得
        mViewModel = ViewModelProviders.of(this, factory).get(GitHubViewModel::class.java)
//        mViewModel = GitHubViewModel(application, this)
        binding!!.viewModel = mViewModel

        setSupportActionBar(binding!!.toolbar)
        setupRecyclerView(binding!!.reposRecyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel!!.destroy()
    }

    override fun onRepositoriesChanged(repositories: List<GitHubRepositoryModel>?) {
        val adapter = binding!!.reposRecyclerView.adapter as GitHubRepositoryAdapter
        adapter.setRepositories(repositories!!)
        adapter.notifyDataSetChanged()
        hideSoftKeyboard()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val adapter = GitHubRepositoryAdapter(application)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun hideSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(binding!!.editTextUsername.windowToken, 0)
    }

}