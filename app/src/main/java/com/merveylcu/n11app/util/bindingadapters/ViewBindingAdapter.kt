package com.merveylcu.n11app.util.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.merveylcu.n11app.util.listener.OnSingleClickListener

@BindingAdapter("visibility")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("onSingleClick")
fun View.setOnSingleClickListener(onSingleClickListener: OnSingleClickListener) {
    setOnClickListener(onSingleClickListener)
}