package com.merveylcu.n11app.ui.search.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.database.FirebaseDatabase
import com.merveylcu.n11app.data.dao.UserDao
import com.merveylcu.n11app.data.model.search.User
import com.merveylcu.n11app.service.api.UserApi
import com.merveylcu.n11app.service.util.AppResult
import com.merveylcu.n11app.service.util.NetworkHandler

class UserPagingSource(
    private val dao: UserDao,
    private val api: UserApi,
    private val searchUserName: String
) :
    PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val pageNumber = params.key ?: 0
        return try {
            val result = if (searchUserName.isNotEmpty()) {
                NetworkHandler.sendRequest(
                    request = { api.getUsers(searchUserName, pageNumber) },
                    isAsync = pageNumber != 0,
                    isShowErrorDialog = pageNumber == 0
                )
            } else {
                null
            }
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("users")
            val response = when (result) {
                is AppResult.Success -> {
                    result.successData?.items?.let {
                        dao.insertUsers(it)
                        it.forEach { user ->
                            myRef.child(user.login).setValue(user)
                        }
                    }
                    result.successData
                }
                else -> {
                    null
                }
            }

            if (response == null || (pageNumber == 0 && response.items.isNullOrEmpty())) {
                LoadResult.Error(Exception())
            } else {
                LoadResult.Page(
                    data = response.items,
                    prevKey = if (pageNumber == 0) null else pageNumber - 1,
                    nextKey = if (response.items.isNullOrEmpty()
                        || response.incomplete_results
                        || response.items.size == response.total_count
                    )
                        null else pageNumber + 1
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}