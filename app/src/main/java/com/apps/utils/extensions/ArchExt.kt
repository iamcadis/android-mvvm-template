package com.apps.utils.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.apps.constants.ErrorCode
import com.apps.data.remote.error.ErrorManager
import com.apps.data.remote.error.ErrorResponse
import com.apps.data.remote.error.mapper.ErrorMapper
import com.apps.interfaces.OnLostConnection
import com.apps.ui.extra.LostConnectionSheet

fun Context.hasPermissions(vararg permissions: String): Boolean = permissions.all {
    ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

fun Context?.showToast(msg: String?) {
    val handler = Handler(Looper.getMainLooper())
    handler.post {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

fun AppCompatActivity.observeError(
    errorLiveData: LiveData<ErrorResponse>?,
    onLostConnection: OnLostConnection
){
    this.observeError(this, errorLiveData, this.supportFragmentManager, onLostConnection)
}

fun Fragment.observeError(
    errorLiveData: LiveData<ErrorResponse>?,
    onLostConnection: OnLostConnection
){
    this.observeError(this.context, errorLiveData, this.childFragmentManager, onLostConnection)
}

fun <T> LifecycleOwner.observeData(liveData: LiveData<T>?, action: (t: T) -> Unit) {
    liveData?.observe(this, { element ->
        element?.let { action(it) }
    })
}

/**
 * for private extensions
 */
private fun LifecycleOwner.observeError(
    context: Context?,
    errorLiveData: LiveData<ErrorResponse>?,
    fragmentManager: FragmentManager,
    onLostConnection: OnLostConnection
) {
    errorLiveData?.observe(this, {
        if (it.code == ErrorCode.NO_INTERNET_CONNECTION) {
            val sheet = LostConnectionSheet.newInstance(it.passingCode, onLostConnection)
            sheet.showNow(fragmentManager, sheet.javaClass.simpleName)
        } else {
            val err = ErrorManager(ErrorMapper(context))
            context.showToast(err.getError(it.code).message)
        }
    })
}