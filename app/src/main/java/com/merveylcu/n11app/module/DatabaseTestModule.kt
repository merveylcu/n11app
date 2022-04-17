package com.merveylcu.n11app.module

import androidx.room.Room
import com.merveylcu.n11app.data.database.UserDatabase
import org.koin.dsl.module

val roomTestModule = module {
    single {
        Room.inMemoryDatabaseBuilder(get(), UserDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}