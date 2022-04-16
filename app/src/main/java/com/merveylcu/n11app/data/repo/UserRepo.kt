package com.merveylcu.n11app.data.repo

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.firebase.database.DatabaseReference
import com.merveylcu.n11app.data.dao.UserDao
import com.merveylcu.n11app.data.model.detail.UserDetailResponse
import com.merveylcu.n11app.data.model.search.User
import com.merveylcu.n11app.service.api.UserApi
import com.merveylcu.n11app.service.util.AppResult
import com.merveylcu.n11app.service.util.NetworkHandler
import com.merveylcu.n11app.ui.search.adapter.UserPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface UserRepo {

    suspend fun getUsersFromApi(userName: String): Flow<PagingData<User>>

    suspend fun getUserDetailFromApi(userName: String): AppResult<UserDetailResponse>

    suspend fun saveUsersToDB(users: PagingData<User>)

    suspend fun saveUsersToFirebase(users: PagingData<User>)
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
                    api, userName
                )
            }
        )
        pager.flow.map {
            saveUsersToDB(it)
            saveUsersToFirebase(it)
        }
        return pager.flow
    }

    override suspend fun getUserDetailFromApi(userName: String): AppResult<UserDetailResponse> {
        return NetworkHandler.sendRequest(context, { api.getUserDetail(userName) })
    }

    override suspend fun saveUsersToDB(users: PagingData<User>) {
        users.map { user ->
            dao.insertUser(user)
        }
    }

    override suspend fun saveUsersToFirebase(users: PagingData<User>) {
        users.map { user ->
            frb.child(user.login).setValue(user)
        }
    }

}