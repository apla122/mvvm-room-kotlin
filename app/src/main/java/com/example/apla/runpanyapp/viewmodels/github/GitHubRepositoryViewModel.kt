package com.example.apla.runpanyapp.viewmodels.github

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import android.view.View
import android.widget.ImageView
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.models.GitHubRepositoryModel
import com.example.apla.runpanyapp.viewmodels.BaseViewModel
import com.squareup.picasso.Picasso
import io.reactivex.disposables.Disposable

/**
 * ViewModel for the RepositoryActivity
 */
class GitHubRepositoryViewModel(app: Application, repository: GitHubRepositoryModel) : BaseViewModel(app) {

    private val TAG = "RepositoryViewModel"

    private var repository: GitHubRepositoryModel = repository
    private var context: Context? = app.applicationContext
    private var subscription: Disposable? = null

    var ownerName: ObservableField<String>
    var ownerEmail: ObservableField<String>
    var ownerLocation: ObservableField<String>
    var ownerEmailVisibility: ObservableInt
    var ownerLocationVisibility: ObservableInt
    var ownerLayoutVisibility: ObservableInt

    init {
        this.ownerName = ObservableField()
        this.ownerEmail = ObservableField()
        this.ownerLocation = ObservableField()
        this.ownerLayoutVisibility = ObservableInt(View.INVISIBLE)
        this.ownerEmailVisibility = ObservableInt(View.VISIBLE)
        this.ownerLocationVisibility = ObservableInt(View.VISIBLE)
        // Trigger loading the rest of the getUser data as soon as the view model is created.
        // It's odd having to trigger this from here. Cases where accessing to the data model
        // needs to happen because of a change in the Activity/Fragment lifecycle
        // (i.e. an activity created) don't work very well with this MVVM pattern.
        // It also makes this class more difficult to test. Hopefully a better solution will be found
        if (repository.owner != null) {
            loadFullUser(repository.owner!!.url!!)
        }

    }

    fun getDescription(): String {
        return  if (repository!!.description != null) repository!!.description!! else ""
    }

    fun getHomepage(): String {
        return if (repository!!.homepage != null) repository!!.homepage!! else ""
    }

    fun getHomepageVisibility(): Int {
        return if (repository!!.hasHomepage()) View.VISIBLE else View.GONE
    }

    fun getLanguage(): String {
        return if (repository!!.language != null) context!!.getString(R.string.text_language, repository!!.language) else ""
    }

    fun getLanguageVisibility(): Int {
        return if (repository!!.hasLanguage()) View.VISIBLE else View.GONE
    }

    fun getForkVisibility(): Int {
        return if (repository!!.isFork()) View.VISIBLE else View.GONE
    }

    fun getOwnerAvatarUrl(): String {
        var avatarUrl = ""
        if (repository.owner != null) {
            avatarUrl = repository.owner!!.avatarUrl!!
        }
        return avatarUrl
    }

    override fun destroy() {
        this.context = null
        if (subscription != null && !subscription!!.isDisposed) subscription!!.dispose()
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String) {
        Picasso.with(view.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(view)
    }

    private fun loadFullUser(url: String) {

    }

    class Factory(private val application: Application, private val repository: GitHubRepositoryModel) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return GitHubRepositoryViewModel(application, repository) as T
        }
    }

}
