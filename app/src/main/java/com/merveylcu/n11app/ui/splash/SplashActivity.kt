package com.merveylcu.n11app.ui.splash

import com.merveylcu.n11app.R
import com.merveylcu.n11app.databinding.ActivitySplashBinding
import com.merveylcu.n11app.ui.base.BaseActivity
import com.merveylcu.n11app.ui.base.VMState
import com.merveylcu.n11app.ui.home.HomePageActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override val layoutRes: Int
        get() = R.layout.activity_splash

    override val viewModel: SplashViewModel by viewModel()

    override fun initUI() {
        supportActionBar?.hide()
    }

    override fun initListener() {}

    override fun onChangedScreenState(state: VMState) {
        when (state) {
            is SplashVMState.OpenHomePage -> {
                HomePageActivity.open(this)
            }
        }
    }

}