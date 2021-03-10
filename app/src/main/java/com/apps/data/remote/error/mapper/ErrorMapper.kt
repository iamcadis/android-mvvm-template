package com.apps.data.remote.error.mapper

import android.content.Context
import com.apps.R
import com.apps.constants.ErrorCode
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorMapper @Inject constructor(@ApplicationContext private val context: Context) :
    ErrorMapperInterface {

    override fun getErrorString(errorId: Int): String {
        return context.getString(errorId)
    }

    override val errorsMap: Map<Int, String?>
        get() = mapOf(
            Pair(ErrorCode.EXPIRED_TOKEN_AUTH, getErrorString(R.string.err_token_expired)),
            Pair(ErrorCode.INVALID_CREDENTIALS, getErrorString(R.string.err_invalid_credentials)),
            Pair(ErrorCode.INSUFFICIENT_PERMISSION_ERROR_CODE, getErrorString(R.string.err_forbidden_access))
        ).withDefault {
            getErrorString(R.string.err_network)
        }
}
