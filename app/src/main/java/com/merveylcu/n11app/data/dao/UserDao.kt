package com.merveylcu.n11app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.merveylcu.n11app.data.model.search.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)

    @Query("UPDATE Users SET isFavorite=:isFavorite WHERE login = :userName")
    fun setUserFavorite(userName: String, isFavorite: Boolean)

    @Query("SELECT isFavorite FROM Users WHERE login = :userName")
    fun getUserFavorite(userName: String): Boolean

}