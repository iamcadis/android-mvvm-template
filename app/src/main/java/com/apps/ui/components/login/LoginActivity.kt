package com.apps.ui.components.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.apps.R
import com.apps.data.local.UserPrefs
import com.apps.data.remote.ResultWrapper
import com.apps.databinding.ActivityLoginBinding
import com.apps.interfaces.OnLostConnection
import com.apps.ui.base.BaseActivity
import com.apps.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>({ ActivityLoginBinding.inflate(it) }), OnLostConnection {

    @Inject
    lateinit var userPrefs: UserPrefs

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun ActivityLoginBinding.onCreate(savedInstanceState: Bundle?) {
        val viewModel: LoginViewModel by viewModels()
        binding.viewModel = viewModel

        // observe viewModel
        observeData(binding.viewModel?.loginLiveData, ::handleResponseLogin)
        observeError(binding.viewModel?.errorLiveData, this@LoginActivity)

        setLoginUsingBioMetric()
    }

    override fun onRetry(passingCode: String?) {
        binding.viewModel?.login()
    }

    private fun handleResponseLogin(res: ResultWrapper<String>) {
        when(res) {
            is ResultWrapper.Loading -> this.showLoading()
            is ResultWrapper.Success -> {
                this.hideLoading()
                userPrefs.authToken = res.data?.getJsonResponse(key = "token")
                this.backToHome()
            }
            is ResultWrapper.Error -> {
                this.hideLoading()
                binding.viewModel?.setError(res.error)
            }
        }
    }

    private fun setLoginUsingBioMetric() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    backToHome()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.login_fingerprint_title))
            .setNegativeButtonText("Use password")
            .build()

        binding.loginFingerprint.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}