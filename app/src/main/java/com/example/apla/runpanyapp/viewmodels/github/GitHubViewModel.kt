package com.example.apla.runpanyapp.viewmodels.github

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.common.utils.OnOkInSoftKeyboardListener
import com.example.apla.runpanyapp.data.GitHubServiceRepository
import com.example.apla.runpanyapp.models.GitHubRepositoryModel
import com.example.apla.runpanyapp.viewmodels.BaseViewModel
import io.reactivex.disposables.Disposable
import org.modelmapper.ModelMapper
import retrofit2.HttpException

/**
 * View model for the MainActivity
 */
class GitHubViewModel(val app: Application, dataListener: DataListener) : BaseViewModel(app) {

    private val TAG = "MainViewModel"

    var infoMessageVisibility: ObservableInt
    var progressVisibility: ObservableInt
    var recyclerViewVisibility: ObservableInt
    var searchButtonVisibility: ObservableInt
    var infoMessage: ObservableField<String>

    private var dataListener: DataListener? = dataListener

    private var subscription: Disposable? = null
    private var repositories: List<GitHubRepositoryModel>? = null
    private var editTextUsernameValue: String? = null

    init {
        infoMessageVisibility = ObservableInt(View.VISIBLE)
        progressVisibility = ObservableInt(View.INVISIBLE)
        recyclerViewVisibility = ObservableInt(View.INVISIBLE)
        searchButtonVisibility = ObservableInt(View.GONE)
        infoMessage = ObservableField(app.getString(R.string.default_info_message))
    }

    fun setDataListener(dataListener: DataListener) {
        this.dataListener = dataListener
    }

    override fun destroy() {
        if (subscription != null && !subscription!!.isDisposed) subscription!!.dispose()
        subscription = null
        dataListener = null
    }

    fun onSearchAction(view: TextView, actionId: Int, event: KeyEvent): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val username = view.text.toString()
            if (username.length > 0) loadGithubRepos(username)
            return true
        }
        return false
    }

    fun onClickSearch(view: View) {
        loadGithubRepos(editTextUsernameValue!!)
    }

    fun getUsernameEditTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                editTextUsernameValue = charSequence.toString()
                searchButtonVisibility.set(if (charSequence.length > 0) View.VISIBLE else View.GONE)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        }
    }

    private fun loadGithubRepos(userName: String) {
        progressVisibility.set(View.VISIBLE)
        recyclerViewVisibility.set(View.INVISIBLE)
        infoMessageVisibility.set(View.INVISIBLE)
        subscription = GitHubServiceRepository().requestRepository(userName, 1)
                .subscribe(
                        { entities ->
                            Log.i(TAG, "Repos loaded $entities")
                            var repositories = mutableListOf<GitHubRepositoryModel>()
                            for (entity in entities) {
                                val modelMapper = ModelMapper()
                                val model = modelMapper.map(entity, GitHubRepositoryModel::class.java)
                                repositories.add(model)
                            }
                            this@GitHubViewModel.repositories = repositories
                        },
                        { error ->
                            Log.e(TAG, "Error loading GitHub repos ", error)
                            progressVisibility.set(View.INVISIBLE)
                            if (isHttp404(error)) {
                                infoMessage.set(app.getString(R.string.error_username_not_found))
                            } else {
                                infoMessage.set(app.getString(R.string.error_loading_repos))
                            }
                            infoMessageVisibility.set(View.VISIBLE)
                        },
                        {
                            if (dataListener != null)
                                this@GitHubViewModel.dataListener!!.onRepositoriesChanged(repositories)
                            progressVisibility.set(View.INVISIBLE)
                            if (!repositories!!.isEmpty()) {
                                recyclerViewVisibility.set(View.VISIBLE)
                            } else {
                                infoMessage.set(app.getString(R.string.text_empty_repos))
                                infoMessageVisibility.set(View.VISIBLE)
                            }
                        })

    }

    val ss = object : OnOkInSoftKeyboardListener() {
        override fun onOkInSoftKeyboard(view: View) {
            onClickSearch(view)
        }
    }

    private fun isHttp404(error: Throwable): Boolean {
        return !(error !is HttpException || error.code() != 404)
    }

    interface DataListener {
        fun onRepositoriesChanged(repositories: List<GitHubRepositoryModel>?)
    }

    class Factory(private val application: Application, private val dataListener: DataListener) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return GitHubViewModel(application, dataListener) as T
        }
    }

}
