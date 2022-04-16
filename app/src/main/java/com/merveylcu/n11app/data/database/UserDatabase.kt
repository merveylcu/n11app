package com.merveylcu.n11app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.merveylcu.n11app.data.dao.UserDao
import com.merveylcu.n11app.data.model.search.User

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}