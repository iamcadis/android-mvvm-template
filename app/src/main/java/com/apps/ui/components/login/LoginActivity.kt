package com.apps.ui.components.login

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.apps.R
import com.apps.data.local.UserPrefs
import com.apps.data.remote.ResultWrapper
import com.apps.databinding.ActivityLoginBinding
import com.apps.interfaces.OnLostConnection
import com.apps.ui.base.BaseActivity
import com.apps.utils.AesEncryption
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
    private lateinit var role: String

    override fun ActivityLoginBinding.onCreate(savedInstanceState: Bundle?) {
        val viewModel: LoginViewModel by viewModels()
        binding.viewModel = viewModel

        // observe viewModel
        observeData(binding.viewModel?.loginLiveData, ::handleResponseLogin)
        observeError(binding.viewModel?.errorLiveData, this@LoginActivity)

        checkIsFingerPrintAvailable()
        setLoginUsingFingerPrint()
    }

    override fun onRetry(passingCode: String?) {
        binding.viewModel?.login()
    }

    private fun handleResponseLogin(res: ResultWrapper<String>) {
        when(res) {
            is ResultWrapper.Loading -> this.showLoading()
            is ResultWrapper.Success -> {
                this.hideLoading()
                this.saveCredential(res.data)
                this.backToHome()
            }
            is ResultWrapper.Error -> {
                this.hideLoading()
                binding.viewModel?.setError(res.error)
            }
        }
    }

    private fun checkIsFingerPrintAvailable() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                binding.loginFingerprint.visibility = View.VISIBLE
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                binding.loginFingerprint.visibility = View.GONE
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                binding.loginFingerprint.visibility = View.GONE
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                binding.loginFingerprint.visibility = View.GONE
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED ->
                binding.loginFingerprint.visibility = View.GONE
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED ->
                binding.loginFingerprint.visibility = View.GONE
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN ->
                binding.loginFingerprint.visibility = View.GONE
        }
    }

    private fun setLoginUsingFingerPrint() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    binding.viewModel?.setUserUsingFingerPrint()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.login_fingerprint_title))
            .setNegativeButtonText(getString(R.string.cancel))
            .build()

        binding.loginFingerprint.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun saveCredential(data: String?) {
        userPrefs.authToken = data?.getJsonResponse(key = "token")
        userPrefs.username = binding.viewModel?.username ?: ""
        userPrefs.password = AesEncryption.encrypt(binding.viewModel?.password) ?: ""
        userPrefs.role = role
    }
}