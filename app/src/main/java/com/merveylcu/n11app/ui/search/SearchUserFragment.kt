package com.merveylcu.n11app.ui.search

import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.merveylcu.n11app.R
import com.merveylcu.n11app.databinding.FragmentSearchUserBinding
import com.merveylcu.n11app.ui.base.BaseFragment
import com.merveylcu.n11app.ui.base.VMState
import com.merveylcu.n11app.ui.search.adapter.UserListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchUserFragment : BaseFragment<SearchUserViewModel, FragmentSearchUserBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_search_user

    override val viewModel: SearchUserViewModel by viewModel()

    override fun initUI() {}

    override fun initListener() {
        binding?.etSearch?.doAfterTextChanged {
            it?.let {
                viewModel.search(it.toString())
            }
        }
    }

    override fun onChangedScreenState(state: VMState) {
        when (state) {
            is SearchUserVMState.SetUserList -> {
                lifecycleScope.launch {
                    setUserListAdapter()
                }
            }
            is SearchUserVMState.OpenUserDetail -> {
                findNavController().navigate(
                    SearchUserFragmentDirections.actionSearchUserFragmentToUserDetailFragment(
                        state.userName
                    )
                )
            }
        }
    }

    private suspend fun setUserListAdapter() {
        val transactionAdapter = UserListAdapter(viewModel.onUser)
        transactionAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Error) {
                viewModel.isEmptyUser.value = true
            }
        }
        binding?.recyclerView?.apply {
            adapter = transactionAdapter
        }
        viewModel.userList.collectLatest { pagingData ->
            viewModel.isEmptyUser.value = false
            transactionAdapter.submitData(pagingData)
        }
    }

}