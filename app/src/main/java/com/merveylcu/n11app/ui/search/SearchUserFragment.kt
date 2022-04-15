package com.merveylcu.n11app.ui.search

import android.text.Editable
import android.text.TextWatcher
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
import java.util.*

class SearchUserFragment : BaseFragment<SearchUserViewModel, FragmentSearchUserBinding>() {

    private var timer: Timer? = null

    override val layoutRes: Int
        get() = R.layout.fragment_search_user

    override val viewModel: SearchUserViewModel by viewModel()

    override fun initUI() {}

    override fun initListener() {
        binding?.etSearch?.addTextChangedListener(searchTextWatcher)
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

    private val searchTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(arg0: Editable) {
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    lifecycleScope.launch {
                        viewModel.search(arg0.toString())
                    }
                }
            }, 600)
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            timer?.cancel()
        }
    }

}