package com.merveylcu.n11app.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.merveylcu.n11app.ui.dialog.ProgressLoadingDialog
import com.merveylcu.n11app.core.Constants

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    private val progress: ProgressLoadingDialog by lazy {
        ProgressLoadingDialog(context = this)
    }

    protected var binding: DB? = null

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected abstract val viewModel: VM

    @UiThread
    protected abstract fun initUI()

    protected abstract fun initListener()

    protected abstract fun onChangedScreenState(state: VMState)

    private val observer = Observer<VMState> {
        onChangedScreenState(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Constants.App.latestActivity = this

        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding?.setVariable(BR.viewModel, viewModel)
        binding?.lifecycleOwner = this
        viewModel.state.observe(this, observer)

        initUI()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        Constants.App.latestActivity = this
    }

    override fun onDestroy() {
        viewModel.state.removeObserver(observer)
        super.onDestroy()
    }

    fun showLoading() {
        runOnUiThread {
            progress.toggle(status = true)
        }
    }

    fun hideLoading() {
        runOnUiThread {
            progress.release()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}