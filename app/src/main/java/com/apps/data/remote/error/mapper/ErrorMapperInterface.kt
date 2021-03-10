package com.apps.data.remote.error.mapper

interface ErrorMapperInterface {
    fun getErrorString(errorId: Int): String?
    val errorsMap: Map<Int, String?>
}
