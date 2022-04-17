package com.merveylcu.n11app.data.repo

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.database.DatabaseReference
import com.merveylcu.n11app.data.dao.UserDao
import com.merveylcu.n11app.data.model.detail.UserDetailResponse
import com.merveylcu.n11app.data.model.search.User
import com.merveylcu.n11app.service.api.UserApi
import com.merveylcu.network.AppResult
import com.merveylcu.network.NetworkHandler
import com.merveylcu.n11app.ui.search.adapter.UserPagingSource
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    suspend fun getUsersFromApi(userName: String): Flow<PagingData<User>>

    suspend fun getUserDetailFromApi(userName: String): AppResult<UserDetailResponse>

    suspend fun saveUsersToDB(users: List<User>)

    suspend fun saveUsersToFirebase(users: List<User>)

    suspend fun setUserFavorite(userName: String, isFavorite: Boolean)

    suspend fun getUserFavorite(userName: String): Boolean
}

class UserRepoImpl(
    private val context: Context,
    private val dao: UserDao,
    private val frb: DatabaseReference,
    private val api: UserApi
) : UserRepo {
    override suspend fun getUsersFromApi(userName: String): Flow<PagingData<User>> {
        val pager = Pager(config = PagingConfig(pageSize = 50, enablePlaceholders = false),
            pagingSourceFactory = {
                UserPagingSource(
                    this, api, userName
                )
            }
        )
        return pager.flow
    }

    override suspend fun getUserDetailFromApi(userName: String): AppResult<UserDetailResponse> {
        return NetworkHandler.sendRequest(context, { api.getUserDetail(userName) })
    }

    override suspend fun saveUsersToDB(users: List<User>) {
        users.map { user ->
            user.isFavorite = getUserFavorite(user.login)
        }
        dao.addUsers(users)
    }

    override suspend fun saveUsersToFirebase(users: List<User>) {
        users.map { user ->
            user.isFavorite = getUserFavorite(user.login)
            frb.child(user.login).setValue(user)
        }
    }

    override suspend fun setUserFavorite(userName: String, isFavorite: Boolean) {
        dao.setUserFavorite(userName, isFavorite)
    }

    override suspend fun getUserFavorite(userName: String): Boolean {
        return dao.getUserFavorite(userName)
    }

}