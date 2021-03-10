package com.apps.ui.extra

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.apps.R

class LoadingDialog(context: Context) {

    private val dialog = Dialog(context, R.style.Theme_App_LoadingDialog)

    fun show() { dialog.show() }

    fun hide() { dialog.dismiss() }

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.setCancelable(false)
    }
}