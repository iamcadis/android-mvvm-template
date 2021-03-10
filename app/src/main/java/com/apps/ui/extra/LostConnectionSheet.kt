package com.apps.ui.extra

import android.os.Bundle
import androidx.core.os.bundleOf
import com.apps.databinding.SheetLostConnectionBinding
import com.apps.interfaces.OnLostConnection
import com.apps.ui.base.BaseSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LostConnectionSheet: BaseSheetDialog<SheetLostConnectionBinding>({ SheetLostConnectionBinding.inflate(it) }) {

    private var onLostConnection: OnLostConnection? = null

    override fun SheetLostConnectionBinding.onViewCreated(savedInstanceState: Bundle?) {
        val code = arguments?.getString(EXTRA_PASSING_CODE_API)

        binding.retry.setOnClickListener {
            onLostConnection?.onRetry(passingCode = code)
            dismiss()
        }
    }

    companion object {
        private const val EXTRA_PASSING_CODE_API = "key.EXTRA_PASSING_CODE_API"

        fun newInstance(passingCode: String?, onLostConnection: OnLostConnection?) = LostConnectionSheet().apply {
            this.arguments = bundleOf(EXTRA_PASSING_CODE_API to passingCode)
            this.onLostConnection = onLostConnection
        }
    }
}