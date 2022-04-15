package com.merveylcu.n11app.ui.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer

abstract class BaseDialogFragment : DialogFragment() {

    private val observer = Observer<VMState> {
        onChangedScreenState(it)
    }

    protected abstract fun getVM(): BaseViewModel

    protected open fun onChangedScreenState(state: VMState) {}

    protected fun getBaseActivity() = activity as BaseActivity<*, *>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getVM().state.observe(viewLifecycleOwner, observer)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getVM().state.removeObserver(observer)
    }
}