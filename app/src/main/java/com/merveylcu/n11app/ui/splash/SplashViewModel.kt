package com.merveylcu.n11app.ui.splash

import androidx.lifecycle.viewModelScope
import com.merveylcu.n11app.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel : BaseViewModel() {

    init {
        viewModelScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                state.value = SplashVMState.OpenHomePage()
            }
        }
    }

}