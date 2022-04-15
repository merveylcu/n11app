package com.merveylcu.n11app.ui.detail

import androidx.navigation.fragment.navArgs
import com.merveylcu.n11app.R
import com.merveylcu.n11app.databinding.FragmentUserDetailBinding
import com.merveylcu.n11app.ui.base.BaseFragment
import com.merveylcu.n11app.ui.base.VMState
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : BaseFragment<UserDetailViewModel, FragmentUserDetailBinding>() {

    private val args: UserDetailFragmentArgs by navArgs()

    override val layoutRes: Int
        get() = R.layout.fragment_user_detail

    override val viewModel: UserDetailViewModel by viewModel()

    override fun initUI() {
        viewModel.getUserDetail(args.userName)
    }

    override fun initListener() {}

    override fun onChangedScreenState(state: VMState) {}

}