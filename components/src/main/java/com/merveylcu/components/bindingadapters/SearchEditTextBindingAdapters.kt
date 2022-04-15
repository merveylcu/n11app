package com.merveylcu.components.bindingadapters

import android.text.TextWatcher
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.merveylcu.components.searchedittext.SearchEditText
import com.merveylcu.components.searchedittext.VoidCallback

@BindingAdapter("hint")
fun setHint(editText: SearchEditText, resId: Int) {
    editText.setHint(resId)
}

@BindingAdapter("leftIcon")
fun setLeftIcon(editText: SearchEditText, resId: Int) {
    editText.setLeftIcon(resId)
}

@BindingAdapter("leftIconVisibility")
fun setLeftIconVisibility(editText: SearchEditText, visibility: Boolean) {
    editText.setLeftIconVisibility(visibility)
}

@BindingAdapter("onLeftIconClick")
fun setLeftIconClickListener(editText: SearchEditText, listener: View.OnClickListener) {
    editText.setLeftIconClickListener(listener)
}

@BindingAdapter("textWatcher")
fun setTextWatcher(editText: SearchEditText, textWatcher: TextWatcher) {
    editText.setTextChangeListener(textWatcher)
}

//region text
@BindingAdapter("text")
fun setText(editText: SearchEditText, text: String) {
    if (editText.getText() != text) {
        editText.setText(text)
    }
}

@InverseBindingAdapter(attribute = "text")
fun getText(editText: SearchEditText): String {
    return editText.getText()
}

@BindingAdapter("app:textAttrChanged")
fun textAttrChanged(editText: SearchEditText, attrChange: InverseBindingListener) {
    editText.setTextAttrChangeListener(object : VoidCallback {
        override fun callback() {
            attrChange.onChange()
        }
    })
}
//endregion