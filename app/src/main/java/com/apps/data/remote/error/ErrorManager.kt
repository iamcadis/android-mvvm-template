package com.apps.data.remote.error

import com.apps.data.remote.error.mapper.ErrorMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorFactory {
    override fun getError(errorCode: Int): Error {
        val message = errorMapper.errorsMap.getValue(errorCode) ?: "Something has error"
        return Error(code = errorCode, message = message)
    }
}
