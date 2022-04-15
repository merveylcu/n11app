package com.merveylcu.n11app.ui.splash

import com.merveylcu.n11app.ui.base.BaseViewModel

class SplashViewModel : BaseViewModel() {

    init {
        state.value = SplashVMState.OpenHomePage()
    }

}