package com.merveylcu.n11app.module

import android.content.Context
import com.merveylcu.n11app.data.repo.N11Repo
import com.merveylcu.n11app.data.repo.N11RepoImpl
import com.merveylcu.n11app.service.api.GithubUserApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideN11Repository(
        context: Context,
        api: GithubUserApi
    ): N11Repo {
        return N11RepoImpl(context, api)
    }
    single { provideN11Repository(androidContext(), get()) }
}