package com.apps.data.remote

import com.apps.data.remote.error.ErrorResponse

sealed class ResultWrapper<T>(
    val data: String? = null,
    val error: ErrorResponse? = null,
) {
    class Success(data: String) : ResultWrapper<String>(data = data)
    class Loading(data: String? = null) : ResultWrapper<String>(data = data)
    class Error<T>(url: String, errorCode: Int) : ResultWrapper<T>(error = ErrorResponse(url, errorCode))

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[code=${error?.code};url=${error?.url}]"
            is Loading -> "Loading"
        }
    }
}