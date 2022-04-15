package com.merveylcu.n11app.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.merveylcu.n11app.databinding.DialogProgressLoadingBinding

class ProgressLoadingDialog(context: Context) : Dialog(context), LifecycleObserver {

    private var binding: DialogProgressLoadingBinding =
        DialogProgressLoadingBinding.inflate(LayoutInflater.from(context))

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(true)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = lp
    }

    fun toggle(status: Boolean = true) {
        if (status) {
            if (!isShowing) {
                show()
            }
        } else {
            cancel()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun release() {
        cancel()
    }

}