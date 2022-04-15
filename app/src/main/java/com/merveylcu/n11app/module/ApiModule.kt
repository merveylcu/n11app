package com.merveylcu.n11app.module

import com.merveylcu.n11app.service.api.UserApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
    single { provideUserApi(get()) }
}