package com.apps.ui.components.login

import android.os.Bundle
import androidx.activity.viewModels
import com.apps.databinding.ActivityLoginBinding
import com.apps.interfaces.OnLostConnection
import com.apps.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>({ ActivityLoginBinding.inflate(it) }), OnLostConnection {

    override fun ActivityLoginBinding.onCreate(savedInstanceState: Bundle?) {
        val viewModel: LoginViewModel by viewModels()
        binding.viewModel = viewModel
    }

    override fun onRetry(passingCode: String?) {

    }
}