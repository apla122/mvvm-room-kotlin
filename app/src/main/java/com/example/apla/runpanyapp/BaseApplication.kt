package com.example.apla.runpanyapp

import android.app.Application
import android.content.Context
import com.example.apla.runpanyapp.data.DataRepository
import com.example.apla.runpanyapp.data.local.db.AppDatabase
import com.example.apla.runpanyapp.data.remote.github.GitHubServiceRemoteRepository
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class BaseApplication : Application() {

    private var mAppExecutors: AppExecutors? = null

    override fun onCreate() {
        super.onCreate()

        instance = this
        mAppExecutors = AppExecutors()
    }

    fun getInstance(): BaseApplication? {
        return instance
    }

    //For setting mocks during testing
    var githubService: GitHubServiceRemoteRepository? = null
        get() {
            if (field == null) {
//                this.githubService = GitHubService.Factory.create()
            }
            return field
        }
    private var defaultSubscribeScheduler: Scheduler? = null

    fun defaultSubscribeScheduler(): Scheduler? {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io()
        }
        return defaultSubscribeScheduler
    }

    //User to change scheduler from tests
    fun setDefaultSubscribeScheduler(scheduler: Scheduler) {
        this.defaultSubscribeScheduler = scheduler
    }

    fun getDatabase(): AppDatabase? {
        return AppDatabase.getInstance(this, mAppExecutors!!)
    }

    fun getRepository(): DataRepository {
        return DataRepository.getInstance(getDatabase()!!)
    }

    companion object {

        var instance: BaseApplication? = null
            private set

        operator fun get(context: Context): BaseApplication {
            return context.applicationContext as BaseApplication
        }
    }

}