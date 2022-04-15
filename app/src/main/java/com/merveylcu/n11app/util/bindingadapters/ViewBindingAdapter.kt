package com.merveylcu.n11app.util.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.merveylcu.n11app.util.listener.OnSingleClickListener

@BindingAdapter("onSingleClick")
fun View.setOnSingleClickListener(onSingleClickListener: OnSingleClickListener) {
    setOnClickListener(onSingleClickListener)
}