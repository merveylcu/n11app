package com.merveylcu.n11app.module

import com.merveylcu.n11app.core.Constants
import com.merveylcu.n11app.service.util.NetworkUtil
import com.merveylcu.network.ApiFactory
import org.koin.dsl.module

val networkModule = module {
    single { ApiFactory.provideHttpClient(NetworkUtil.createHeader()) }
    single { ApiFactory.provideRetrofit(Constants.Url.base, get()) }
}