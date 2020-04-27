package com.everyone.cantalk.repository.remote

import com.everyone.cantalk.BuildConfig
import com.everyone.cantalk.model.Response
import com.everyone.cantalk.model.Sender
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

class Client {
    companion object {
        private val retrofit = getRetrofit()

        fun getRetrofit() : Retrofit {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        val service : RetrofitService = retrofit.create(RetrofitService::class.java)
    }
}

interface RetrofitService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:key=" + BuildConfig.AUTH_KEY
    )
    @POST("fcm/send")
    fun sendNotification(@Body body: Sender) : Call<Response>
}