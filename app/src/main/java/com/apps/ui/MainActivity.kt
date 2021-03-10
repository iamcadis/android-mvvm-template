package com.apps.ui

import android.os.Bundle
import com.apps.databinding.ActivityMainBinding
import com.apps.interfaces.OnLostConnection
import com.apps.ui.base.BaseActivity
import com.apps.ui.extra.LostConnectionSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }), OnLostConnection {

    override fun ActivityMainBinding.onCreate(savedInstanceState: Bundle?) {
        val sheet = LostConnectionSheet.newInstance(null, this@MainActivity)
        sheet.showNow(supportFragmentManager, sheet.javaClass.simpleName)
    }

    override fun onRetry(passingCode: String?) {

    }
}