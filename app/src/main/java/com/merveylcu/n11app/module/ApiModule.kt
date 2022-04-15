package com.merveylcu.n11app.module

import com.merveylcu.n11app.service.api.GithubUserApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideN11Api(retrofit: Retrofit): GithubUserApi {
        return retrofit.create(GithubUserApi::class.java)
    }
    single { provideN11Api(get()) }
}