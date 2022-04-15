package com.merveylcu.n11app.core

import android.app.Application
import com.merveylcu.n11app.module.apiModule
import com.merveylcu.n11app.module.networkModule
import com.merveylcu.n11app.module.repositoryModule
import com.merveylcu.n11app.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class N11Application : Application() {

    private val koinModules =
        listOf(apiModule, networkModule, repositoryModule, viewModelModule)

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@N11Application)
            koin.loadModules(koinModules)
        }
    }
}