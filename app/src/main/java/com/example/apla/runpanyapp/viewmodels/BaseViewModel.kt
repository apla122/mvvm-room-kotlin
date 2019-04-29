package com.example.apla.runpanyapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry

/**
 * Interface that every ViewModel must implement
 */
abstract class BaseViewModel(app: Application): AndroidViewModel(app), Observable {

    @delegate:Transient
    private val mCallBacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        mCallBacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        mCallBacks.remove(callback)
    }

    fun notifyChange() {
        mCallBacks.notifyChange(this, 0)
    }

    fun notifyChange(viewId:Int){
        mCallBacks.notifyChange(this, viewId)
    }

    open fun destroy() {}

}