package com.merveylcu.n11app.data.repo

import android.content.Context
import com.merveylcu.n11app.data.model.detail.UserDetailResponse
import com.merveylcu.n11app.data.model.search.SearchUserResponse
import com.merveylcu.n11app.service.api.GithubUserApi
import com.merveylcu.n11app.service.util.AppResult
import com.merveylcu.n11app.service.util.NetworkHandler

interface N11Repo {

    suspend fun getUsers(q: String, page: Int): AppResult<SearchUserResponse>

    suspend fun getUserDetail(userName: String): AppResult<UserDetailResponse>
}

class N11RepoImpl(
    private val context: Context,
    private val api: GithubUserApi
) : N11Repo {
    override suspend fun getUsers(q: String, page: Int): AppResult<SearchUserResponse> {
        return NetworkHandler.sendRequest(context, { api.getUsers(q, page) })
    }

    override suspend fun getUserDetail(userName: String): AppResult<UserDetailResponse> {
        return NetworkHandler.sendRequest(context, { api.getUserDetail(userName) })
    }

}