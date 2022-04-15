package com.merveylcu.n11app.service.util

import android.util.Log
import com.merveylcu.n11app.BuildConfig
import com.merveylcu.n11app.core.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private val headerMap = mutableMapOf<String, String>()
    private const val connectTimeout: Long = 60
    private const val readTimeout: Long = 60

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.Url.base)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)

        addHeaderInterceptor(okHttpClientBuilder)
        addLoggingInterceptor(okHttpClientBuilder)
        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    private fun setHeaders(): MutableMap<String, String> {
        this.headerMap.clear()
        val headerMap = mutableMapOf<String, String>()
        headerMap["Content-Type"] = "application/json"
        headerMap["access_token"] = Constants.Session.token
        this.headerMap.putAll(headerMap)
        return headerMap
    }

    fun getHeaders(): MutableMap<String, String> {
        return headerMap
    }

    private fun addHeaderInterceptor(okHttpClientBuilder: OkHttpClient.Builder) {
        okHttpClientBuilder.addInterceptor {
            val original = it.request()

            val headerMap = setHeaders()
            val requestBuilder = original.newBuilder()
            headerMap.forEach { item ->
                requestBuilder.header(item.key, item.value)
            }
            val request = requestBuilder.method(original.method, original.body).build()
            if (BuildConfig.DEBUG) {
                Log.d("okhttp.OkHttpClient", "headers: ${request.headers}")
            }
            return@addInterceptor it.proceed(request)
        }
    }

    private fun addLoggingInterceptor(okHttpClientBuilder: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
    }

}