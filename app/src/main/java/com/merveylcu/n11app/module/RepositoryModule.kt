package com.merveylcu.n11app.module

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.merveylcu.n11app.data.dao.UserDao
import com.merveylcu.n11app.data.repo.UserRepo
import com.merveylcu.n11app.data.repo.UserRepoImpl
import com.merveylcu.n11app.service.api.UserApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideUserRepository(
        context: Context,
        dao: UserDao,
        frb: DatabaseReference,
        api: UserApi
    ): UserRepo {
        return UserRepoImpl(context, dao, frb, api)
    }
    single { provideUserRepository(androidContext(), get(), get(), get()) }
}