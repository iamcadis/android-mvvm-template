package com.apps.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.apps.databinding.ActivitySplashBinding
import com.apps.ui.base.BaseActivity
import com.apps.utils.extensions.backToHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>({ ActivitySplashBinding.inflate(it) }) {

    override fun ActivitySplashBinding.onCreate(savedInstanceState: Bundle?) {
        openMainActivity()
    }

    private fun openMainActivity() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            backToHome()
        }, 3000)
    }

}