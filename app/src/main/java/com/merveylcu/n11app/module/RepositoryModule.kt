package com.merveylcu.n11app.module

import android.content.Context
import com.merveylcu.n11app.data.repo.UserRepo
import com.merveylcu.n11app.data.repo.UserRepoImpl
import com.merveylcu.n11app.service.api.UserApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideN11Repository(
        context: Context,
        api: UserApi
    ): UserRepo {
        return UserRepoImpl(context, api)
    }
    single { provideN11Repository(androidContext(), get()) }
}