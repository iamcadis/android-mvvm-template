package com.apps.data.remote.error

interface ErrorFactory {
    fun getError(errorCode: Int): Error
}
