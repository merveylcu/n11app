package com.merveylcu.n11app.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.merveylcu.n11app.data.repo.UserRepo
import com.merveylcu.n11app.service.util.AppResult
import com.merveylcu.n11app.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class UserDetailViewModel(private val userRepo: UserRepo) : BaseViewModel() {

    val userName = MutableLiveData("")
    val name = MutableLiveData("")
    val bio = MutableLiveData("")
    val avatarUrl = MutableLiveData("")
    val htmlUrl = MutableLiveData("")

    fun getUserDetail(userName: String) {
        this.userName.value = userName
        viewModelScope.launch {
            when (val result = userRepo.getUserDetail(userName)) {
                is AppResult.Success -> {
                    result.successData?.let {
                        name.value = it.name
                        bio.value = it.bio
                        avatarUrl.value = it.avatar_url
                        htmlUrl.value = it.html_url
                    }
                }
                else -> {}
            }
        }
    }

}