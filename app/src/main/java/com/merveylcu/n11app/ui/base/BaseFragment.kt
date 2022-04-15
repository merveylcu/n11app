package com.merveylcu.n11app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {

    private val observer = Observer<VMState> {
        onChangedScreenState(it)
    }

    protected var binding: DB? = null

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected abstract val viewModel: VM

    protected abstract fun initUI()

    protected abstract fun initListener()

    protected abstract fun onChangedScreenState(state: VMState)

    protected fun getBaseActivity() = requireActivity() as BaseActivity<*, *>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        }
        binding?.lifecycleOwner = this
        binding?.setVariable(BR.viewModel, viewModel)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, observer)

        initUI()
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.state.removeObserver(observer)
    }
}