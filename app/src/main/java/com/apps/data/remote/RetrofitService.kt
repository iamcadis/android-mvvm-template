package com.apps.data.remote

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @GET("{url}")
    suspend fun get(@Path(value = "url", encoded = true) url: String,
                    @QueryMap(encoded = true) query: HashMap<String, String>): Response<String>

    @POST("{url}")
    suspend fun post(@Path(value = "url", encoded = true) url: String,
                     @Body dataInput: RequestBody): Response<String>

    @PUT("{url}")
    suspend fun put(@Path(value = "url", encoded = true) url: String,
                    @Body dataInput: RequestBody): Response<String>

    @DELETE("{url}")
    suspend fun delete(@Path(value = "url", encoded = true) url: String): Response<String>

}