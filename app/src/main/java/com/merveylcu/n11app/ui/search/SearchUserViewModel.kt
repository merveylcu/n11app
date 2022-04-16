package com.merveylcu.n11app.ui.search

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.merveylcu.n11app.data.model.search.User
import com.merveylcu.n11app.data.repo.UserRepo
import com.merveylcu.n11app.ui.base.BaseViewModel
import com.merveylcu.n11app.util.listener.OnItemClickListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

class SearchUserViewModel(private val userRepo: UserRepo) : BaseViewModel() {

    lateinit var userList: Flow<PagingData<User>>
    val searchText = MutableLiveData("")
    val isEmptyUser = MutableLiveData(true)

    suspend fun searchUser(userName: String) {
        userList =
            userRepo.getUsersFromApi(userName).cachedIn(viewModelScope)
        state.value = SearchUserVMState.SetUserList()
    }

    val onUser = object : OnItemClickListener {
        override fun onItemClick(item: Any?, action: String?) {
            val user = item as User
            state.value = SearchUserVMState.OpenUserDetail(user.login)
        }
    }

    val onFavorite = object : OnItemClickListener {
        override fun onItemClick(item: Any?, action: String?) {
            val user = item as User
            viewModelScope.launch {
                userRepo.setUserFavorite(user.login, user.isFavorite)
            }
        }
    }

    private var writingTimer: Timer? = null
    val searchTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            writingTimer = Timer()
            writingTimer?.schedule(object : TimerTask() {
                override fun run() {
                    viewModelScope.launch {
                        searchUser(editable.toString())
                    }
                }
            }, 600)
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            writingTimer?.cancel()
        }
    }

}