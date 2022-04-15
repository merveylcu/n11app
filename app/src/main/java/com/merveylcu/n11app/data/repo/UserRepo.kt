package com.merveylcu.n11app.data.repo

import android.content.Context
import com.merveylcu.n11app.data.model.detail.UserDetailResponse
import com.merveylcu.n11app.data.model.search.SearchUserResponse
import com.merveylcu.n11app.service.api.UserApi
import com.merveylcu.n11app.service.util.AppResult
import com.merveylcu.n11app.service.util.NetworkHandler

interface UserRepo {

    suspend fun getUsers(q: String, page: Int): AppResult<SearchUserResponse>

    suspend fun getUserDetail(userName: String): AppResult<UserDetailResponse>
}

class UserRepoImpl(
    private val context: Context,
    private val api: UserApi
) : UserRepo {
    override suspend fun getUsers(q: String, page: Int): AppResult<SearchUserResponse> {
        return NetworkHandler.sendRequest(context, { api.getUsers(q, page) })
    }

    override suspend fun getUserDetail(userName: String): AppResult<UserDetailResponse> {
        return NetworkHandler.sendRequest(context, { api.getUserDetail(userName) })
    }

}