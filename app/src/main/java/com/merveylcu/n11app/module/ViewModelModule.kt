package com.merveylcu.n11app.module

import com.merveylcu.n11app.ui.detail.UserDetailViewModel
import com.merveylcu.n11app.ui.dialog.DialogViewModel
import com.merveylcu.n11app.ui.home.HomePageViewModel
import com.merveylcu.n11app.ui.search.SearchUserViewModel
import com.merveylcu.n11app.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DialogViewModel() }
    viewModel { SplashViewModel() }
    viewModel { HomePageViewModel() }
    viewModel { SearchUserViewModel(userDao = get(), userApi = get()) }
    viewModel { UserDetailViewModel(userRepo = get()) }
}
