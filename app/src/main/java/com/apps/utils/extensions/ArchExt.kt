package com.apps.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat

fun Context.hasPermissions(vararg permissions: String): Boolean = permissions.all {
    ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

fun Context?.showToast(msg: String?) {
    val handler = Handler(Looper.getMainLooper())
    handler.post {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

fun <A : Activity> Activity?.startNewActivity(activity: Class<A>, bundle: Bundle = Bundle()) {
    Intent(this, activity).also {
        it.putExtras(bundle)
        this?.startActivity(it)
    }
}