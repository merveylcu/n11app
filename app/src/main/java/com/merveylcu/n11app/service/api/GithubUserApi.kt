package com.merveylcu.n11app.service.api

import com.merveylcu.n11app.data.model.detail.UserDetailResponse
import com.merveylcu.n11app.data.model.search.SearchUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserApi {

    @GET("search/users")
    suspend fun getUsers(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 50
    ): Response<SearchUserResponse>

    @GET("users/{user}")
    suspend fun getUserDetail(@Path("userName") userName: String): Response<UserDetailResponse>

}