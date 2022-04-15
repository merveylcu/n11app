package com.merveylcu.n11app.module

import com.merveylcu.n11app.service.util.ApiFactory
import org.koin.dsl.module

val networkModule = module {
    single { ApiFactory.provideHttpClient() }
    single { ApiFactory.provideRetrofit(get()) }
}