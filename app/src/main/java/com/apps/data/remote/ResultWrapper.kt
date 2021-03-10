package com.apps.data.remote

import com.apps.data.remote.error.ErrorResponse

sealed class ResultWrapper<T>(
    val data: String? = null,
    val error: ErrorResponse? = null,
) {
    class Success(data: String) : ResultWrapper<String>(data = data)
    class Loading(data: String? = null) : ResultWrapper<String>(data = data)
    class Error<T>(passingCode: String?, errorCode: Int) : ResultWrapper<T>(
            error = ErrorResponse(passingCode, errorCode)
    )

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[passingCode=${error?.passingCode};errorCode=${error?.code}]"
            is Loading -> "Loading"
        }
    }
}