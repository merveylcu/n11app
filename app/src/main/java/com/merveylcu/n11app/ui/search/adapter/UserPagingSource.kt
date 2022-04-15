package com.merveylcu.n11app.ui.search.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.merveylcu.n11app.data.model.search.User
import com.merveylcu.n11app.service.api.UserApi
import com.merveylcu.n11app.service.util.AppResult
import com.merveylcu.n11app.service.util.NetworkHandler

class UserPagingSource(
    private val api: UserApi,
    private val searchUserName: String
) :
    PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val pageNumber = params.key ?: 0
        return try {
            val result = NetworkHandler.sendRequest(
                request = { api.getUsers(searchUserName, pageNumber) },
                isAsync = pageNumber != 0,
                isShowErrorDialog = pageNumber == 0
            )
            val response = when (result) {
                is AppResult.Success -> {
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
                    nextKey = if (response.items.isNullOrEmpty() || response.incomplete_results) null else pageNumber + 1
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