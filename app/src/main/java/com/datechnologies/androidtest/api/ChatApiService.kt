package com.datechnologies.androidtest.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "https://dev.rapptrlabs.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ChatApiService {
    @GET("Tests/scripts/chat_log.php")
    suspend fun getMessages():
            Response<ChatData>

    @FormUrlEncoded
    @POST("Tests/scripts/login.php")
    suspend fun sendLoginInfo(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>
}

object ChatApi {
    val retrofitService: ChatApiService by lazy {
        retrofit.create(ChatApiService::class.java)
    }
}