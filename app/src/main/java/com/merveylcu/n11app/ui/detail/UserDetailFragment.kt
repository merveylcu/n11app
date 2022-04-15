package com.merveylcu.n11app.ui.detail

import com.merveylcu.n11app.R
import com.merveylcu.n11app.databinding.FragmentUserDetailBinding
import com.merveylcu.n11app.ui.base.BaseFragment
import com.merveylcu.n11app.ui.base.VMState
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : BaseFragment<UserDetailViewModel, FragmentUserDetailBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_user_detail

    override val viewModel: UserDetailViewModel by viewModel()

    override fun initUI() {}

    override fun initListener() {}

    override fun onChangedScreenState(state: VMState) {}

}