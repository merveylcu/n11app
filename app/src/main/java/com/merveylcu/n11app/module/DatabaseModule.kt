package com.merveylcu.n11app.module

import android.app.Application
import androidx.room.Room
import com.merveylcu.n11app.core.Constants
import com.merveylcu.n11app.data.dao.UserDao
import com.merveylcu.n11app.data.database.UserDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDataBase(application: Application): UserDatabase {
        return Room.databaseBuilder(
            application,
            UserDatabase::class.java,
            Constants.App.databaseName
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideDao(dataBase: UserDatabase): UserDao {
        return dataBase.userDao
    }

    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }

}