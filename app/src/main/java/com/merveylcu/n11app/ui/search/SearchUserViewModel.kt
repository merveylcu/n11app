package com.merveylcu.n11app.ui.search

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.merveylcu.n11app.data.dao.UserDao
import com.merveylcu.n11app.data.model.search.User
import com.merveylcu.n11app.service.api.UserApi
import com.merveylcu.n11app.ui.base.BaseViewModel
import com.merveylcu.n11app.ui.search.adapter.UserPagingSource
import com.merveylcu.n11app.util.listener.OnItemClickListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.coroutines.coroutineContext

class SearchUserViewModel(
    private val userDao: UserDao,
    private val userApi: UserApi
) :
    BaseViewModel() {

    private var writingTimer: Timer? = null
    val searchText = MutableLiveData("")
    val isEmptyUser = MutableLiveData(true)
    lateinit var userList: Flow<PagingData<User>>

    val onUser = object : OnItemClickListener {
        override fun onItemClick(item: Any?, action: String?) {
            val user = item as User
            state.value = SearchUserVMState.OpenUserDetail(user.login)
        }
    }

    suspend fun search(userName: String) {
        withContext(coroutineContext) {
            userList =
                Pager(config = PagingConfig(pageSize = 50, enablePlaceholders = false),
                    pagingSourceFactory = {
                        UserPagingSource(
                            userDao, userApi, userName
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            state.value = SearchUserVMState.SetUserList()
        }
    }

    val searchTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(edt: Editable) {
            writingTimer = Timer()
            writingTimer?.schedule(object : TimerTask() {
                override fun run() {
                    viewModelScope.launch {
                        search(edt.toString())
                    }
                }
            }, 600)
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            writingTimer?.cancel()
        }
    }

}