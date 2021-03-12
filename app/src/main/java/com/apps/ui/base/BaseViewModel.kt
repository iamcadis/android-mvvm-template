package com.apps.ui.base

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.apps.data.remote.error.ErrorResponse
import com.apps.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    private val _errorLiveData = SingleLiveEvent<ErrorResponse>()
    val errorLiveData: LiveData<ErrorResponse>
        get() = _errorLiveData

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    fun setError(error: ErrorResponse) {
        _errorLiveData.value = error
    }
}