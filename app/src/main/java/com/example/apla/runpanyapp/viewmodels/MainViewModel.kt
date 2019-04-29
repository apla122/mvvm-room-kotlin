package com.example.apla.runpanyapp.viewmodels

import android.app.Application
import android.content.Intent
import android.view.View
import com.example.apla.runpanyapp.views.github.GitHubMainActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.example.apla.runpanyapp.views.product.ProductMainActivity


class MainViewModel(var app: Application) : BaseViewModel(app) {

    init {
    }

    fun onClickNextView(view: View) {
        val intent = Intent(app, GitHubMainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        app.startActivity(intent)
    }

    fun onClickNextView2(view: View) {
        val intent = Intent(app, ProductMainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        app.startActivity(intent)
    }

    override fun destroy() {

    }

    class Factory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return MainViewModel(application) as T
        }
    }

}
