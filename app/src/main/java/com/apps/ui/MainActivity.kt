package com.apps.ui

import android.os.Bundle
import com.apps.databinding.ActivityMainBinding
import com.apps.interfaces.OnLostConnection
import com.apps.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }), OnLostConnection {

    override fun ActivityMainBinding.onCreate(savedInstanceState: Bundle?) {

    }

    override fun onRetry(passingCode: String?) {

    }
}