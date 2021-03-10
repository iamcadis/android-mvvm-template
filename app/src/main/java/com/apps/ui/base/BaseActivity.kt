package com.apps.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.apps.ui.extra.LoadingDialog

abstract class BaseActivity<B : ViewBinding>(val viewBinder: (LayoutInflater) -> B) : AppCompatActivity() {

    protected lateinit var binding: B
    protected abstract fun B.onCreate(savedInstanceState: Bundle?)
    protected fun showLoading() { loadingDialog?.show() }
    protected fun hideLoading() { loadingDialog?.hide() }

    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewBinder(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog(this)
        binding.onCreate(savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { onBackPressed() }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}