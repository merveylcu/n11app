package com.merveylcu.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private var headerMap = mutableMapOf<String, String>()
    private const val connectTimeout: Long = 60
    private const val readTimeout: Long = 60

    fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun provideHttpClient(headerMap: MutableMap<String, String>): OkHttpClient {
        this.headerMap = headerMap
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)

        addHeaderInterceptor(okHttpClientBuilder)
        addLoggingInterceptor(okHttpClientBuilder)
        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    fun getHeaders(): MutableMap<String, String> {
        return headerMap
    }

    private fun addHeaderInterceptor(okHttpClientBuilder: OkHttpClient.Builder) {
        okHttpClientBuilder.addInterceptor {
            val original = it.request()

            val requestBuilder = original.newBuilder()
            headerMap.forEach { item ->
                requestBuilder.header(item.key, item.value)
            }
            val request = requestBuilder.method(original.method, original.body).build()
            Log.d("okhttp.OkHttpClient", "headers: ${request.headers}")

            return@addInterceptor it.proceed(request)
        }
    }

    private fun addLoggingInterceptor(okHttpClientBuilder: OkHttpClient.Builder) {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }

}