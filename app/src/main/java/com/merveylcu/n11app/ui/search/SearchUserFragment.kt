package com.merveylcu.n11app.ui.search

import com.merveylcu.n11app.R
import com.merveylcu.n11app.databinding.FragmentSearchUserBinding
import com.merveylcu.n11app.ui.base.BaseFragment
import com.merveylcu.n11app.ui.base.VMState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchUserFragment : BaseFragment<SearchUserViewModel, FragmentSearchUserBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_search_user

    override val viewModel: SearchUserViewModel by viewModel()

    override fun initUI() {}

    override fun initListener() {}

    override fun onChangedScreenState(state: VMState) {
        when (state) {
            is SearchUserVMState.OpenUserDetail -> {

            }
        }
    }

}