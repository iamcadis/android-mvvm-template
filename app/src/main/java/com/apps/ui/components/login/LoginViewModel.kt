package com.apps.ui.components.login

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apps.BR
import com.apps.constants.Url
import com.apps.data.dto.Credential
import com.apps.data.remote.RepositorySingleton
import com.apps.data.remote.ResultWrapper
import com.apps.ui.base.BaseViewModel
import com.apps.utils.extensions.isEmailValid
import com.apps.utils.extensions.isPasswordValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: RepositorySingleton
) : BaseViewModel() {

    private val _loginLiveData = MutableLiveData<ResultWrapper<String>>()
    val loginLiveData: LiveData<ResultWrapper<String>>
        get() = _loginLiveData

    private val credential = Credential(username = "", password = "")

    @get:Bindable
    var username: String
        get() = credential.username
        set(value) {
            credential.username = value
            enableLogin = credential.username.isEmailValid && credential.password.isPasswordValid
            notifyPropertyChanged(BR.username)
            notifyPropertyChanged(BR.enableLogin)
        }


    @get:Bindable
    var password: String
        get() = credential.password
        set(value) {
            credential.password = value
            enableLogin = credential.username.isEmailValid && credential.password.isPasswordValid
            notifyPropertyChanged(BR.password)
            notifyPropertyChanged(BR.enableLogin)
        }

    @get:Bindable
    var enableLogin: Boolean = false

    fun login() {
        viewModelScope.launch {
            _loginLiveData.value = ResultWrapper.Loading()
            repository.post(Url.LOGIN, credential)
                .collect {
                    _loginLiveData.value = it
                }
        }
    }

}