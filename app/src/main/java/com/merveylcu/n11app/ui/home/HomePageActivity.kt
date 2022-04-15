package com.merveylcu.n11app.ui.home

import android.app.Activity
import android.content.Intent
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.merveylcu.n11app.R
import com.merveylcu.n11app.databinding.ActivityHomePageBinding
import com.merveylcu.n11app.ui.base.BaseActivity
import com.merveylcu.n11app.ui.base.VMState
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomePageActivity : BaseActivity<HomePageViewModel, ActivityHomePageBinding>() {

    override val layoutRes: Int
        get() = R.layout.activity_home_page

    override val viewModel: HomePageViewModel by viewModel()

    override fun initUI() {
        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment))
    }

    override fun initListener() {}

    override fun onChangedScreenState(state: VMState) {}

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    companion object {
        fun open(activity: Activity) {
            val intent = Intent(activity, HomePageActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }

}