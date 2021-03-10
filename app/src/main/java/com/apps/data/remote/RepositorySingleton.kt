package com.apps.data.remote

import com.apps.constants.ErrorCode
import com.apps.constants.PassingCode
import com.apps.utils.JsonHelper
import com.apps.utils.NetworkHelper
import com.apps.utils.extensions.getJsonResponse
import com.apps.utils.extensions.isHasHTMLTags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.io.IOException
import javax.inject.Inject

class RepositorySingleton @Inject constructor(
    private val retrofitInstance: RetrofitInstance,
    private val networkHelper: NetworkHelper
) {
    suspend fun get(url: String,
                    query: HashMap<String, String>? = null,
                    passingCode: String? = null
    ): Flow<ResultWrapper<String>> {
        return flow {
            val optionsQuery = query ?: HashMap()
            val service = retrofitInstance.create(RetrofitService::class.java)
            emit(
                when (val response = apiCall { service.get(url, optionsQuery) }) {
                    is String -> ResultWrapper.Success(data = response)
                    else -> ResultWrapper.Error(passingCode = passingCode, errorCode = response as Int)
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    suspend fun post(url: String,
                     data: Any,
                     query: HashMap<String, String>? = null,
                     passingCode: String? = null
    ): Flow<ResultWrapper<String>> {
        return flow {
            val optionsQuery = query ?: HashMap()
            val service = retrofitInstance.create(RetrofitService::class.java)
            val dataInput = JsonHelper.toJson(data).toRequestBody(BODY_PARSER_JSON.toMediaTypeOrNull())
            emit(
                when (val response = apiCall { service.post(url, dataInput, optionsQuery) }) {
                    is String -> ResultWrapper.Success(data = response)
                    else -> ResultWrapper.Error(passingCode = passingCode, errorCode = response as Int)
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    suspend fun put(url: String,
                    data: Any,
                    query: HashMap<String, String>? = null,
                    passingCode: String? = null
    ): Flow<ResultWrapper<String>> {
        return flow {
            val optionsQuery = query ?: HashMap()
            val service = retrofitInstance.create(RetrofitService::class.java)
            val dataInput = JsonHelper.toJson(data).toRequestBody(BODY_PARSER_JSON.toMediaTypeOrNull())
            emit(
                    when (val response = apiCall { service.put(url, dataInput, optionsQuery) }) {
                        is String -> ResultWrapper.Success(data = response)
                        else -> ResultWrapper.Error(passingCode = passingCode, errorCode = response as Int)
                    }
            )
        }.flowOn(Dispatchers.IO)
    }

    suspend fun delete(url: String,
                       passingCode: String? = null
    ): Flow<ResultWrapper<String>> {
        return flow {
            val service = retrofitInstance.create(RetrofitService::class.java)
            emit(
                when (val response = apiCall { service.delete(url) }) {
                    is String -> ResultWrapper.Success(data = response)
                    else -> ResultWrapper.Error(passingCode = passingCode, errorCode = response as Int)
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun uploadImageToAmazonS3(url: String, imageFile: File): Flow<ResultWrapper<String>> {
        return flow {

            if (!networkHelper.isNetworkConnected) {
                emit(
                        ResultWrapper.Error(
                                passingCode = PassingCode.UPLOAD_IMAGE,
                                errorCode = ErrorCode.NO_INTERNET_CONNECTION
                        )
                )
                return@flow
            }

            val request = Request.Builder()
                    .header(CONTENT_TYPE, MIME_TYPE_IMAGE)
                    .url(url)
                    .put(imageFile.asRequestBody(MEDIA_TYPE_OCTET.toMediaTypeOrNull()))
                    .build()
            val client = OkHttpClient()
            client.newCall(request).execute().use { response ->
                emit(
                    if (response.isSuccessful) {
                        ResultWrapper.Success(data = response.toString())
                    } else {
                        ResultWrapper.Error(
                                passingCode = PassingCode.UPLOAD_IMAGE,
                                errorCode = response.code
                        )
                    }
                )
                response.body?.close()
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun apiCall(responseCall: suspend () -> Response<*>): Any {
        if (!networkHelper.isNetworkConnected) {
            retrofitInstance.cancelAllRequest()
            return ErrorCode.NO_INTERNET_CONNECTION
        }

        return try {
            val response = responseCall.invoke()
            val errorCode = getErrorCode(response)
            if (response.isSuccessful) response.body() ?: "success" else errorCode
        } catch (e: IOException) {
            when (e) {
                is HttpException -> e.code()
                else -> ErrorCode.RUNTIME_ERROR_CODE
            }
        }
    }

    private fun getErrorCode(response: Response<*>): Int {
        val errorBody = response.errorBody()?.charStream()?.readText()
        return if (errorBody == null) {
            response.code()
        } else {
            if (errorBody.isHasHTMLTags) {
                response.code()
            } else {
                errorBody.getJsonResponse(key = "errorCode")?.toInt() ?: ErrorCode.RUNTIME_ERROR_CODE
            }
        }
    }

    companion object {
        private const val BODY_PARSER_JSON = "application/json; charset=UTF-8"
        private const val CONTENT_TYPE = "Content-Type"
        private const val MIME_TYPE_IMAGE = "image/jpg"
        private const val MEDIA_TYPE_OCTET = "application/octet-stream"
    }
}