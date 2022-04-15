package com.merveylcu.n11app.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.merveylcu.n11app.data.model.search.User
import com.merveylcu.n11app.service.api.UserApi
import com.merveylcu.n11app.ui.base.BaseViewModel
import com.merveylcu.n11app.ui.search.adapter.UserPagingSource
import com.merveylcu.n11app.util.listener.OnItemClickListener
import kotlinx.coroutines.flow.Flow

class SearchUserViewModel(private val userApi: UserApi) : BaseViewModel() {

    val isEmptyUser = MutableLiveData(true)
    lateinit var userList: Flow<PagingData<User>>

    val onUser = object : OnItemClickListener {
        override fun onItemClick(item: Any?, action: String?) {
            val user = item as User
            state.value = SearchUserVMState.OpenUserDetail(user.login)
        }
    }

    fun search(userName: String) {
        userList =
            Pager(config = PagingConfig(pageSize = 50, enablePlaceholders = false),
                pagingSourceFactory = {
                    UserPagingSource(
                        userApi, userName
                    )
                }
            ).flow.cachedIn(viewModelScope)
        state.value = SearchUserVMState.SetUserList()
    }

}