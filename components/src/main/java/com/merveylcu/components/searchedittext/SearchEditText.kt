package com.merveylcu.components.searchedittext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.merveylcu.components.databinding.ComponentSearchEdittextBinding

class SearchEditText : ConstraintLayout {

    private var focusChangeListener: OnFocusChangeListener? = null
    private var textChangeListener: TextWatcher? = null
    private var leftIconClickListener: OnClickListener? = null
    private var textAttrChangeListener: VoidCallback? = null

    private val binding = ComponentSearchEdittextBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        setFocusChangeListener()
        setTextChangeListener()
        setLeftIconClickListener()
    }

    fun getEditText() = binding.et

    fun setText(text: String) {
        binding.et.setText(text)
    }

    fun setTextAttrChangeListener(textAttrChangeListener: VoidCallback) {
        this.textAttrChangeListener = textAttrChangeListener
    }

    fun getText() = binding.et.text.toString()

    fun setFocusChangeListener(focusChangeListener: OnFocusChangeListener) {
        this.focusChangeListener = focusChangeListener
    }

    private fun setFocusChangeListener() {
        binding.et.setOnFocusChangeListener { v, hasFocus ->
            focusChangeListener?.onFocusChange(v, hasFocus)
        }
    }

    fun setTextChangeListener(textChangeListener: TextWatcher) {
        this.textChangeListener = textChangeListener
    }

    private fun setTextChangeListener() {
        binding.et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                textChangeListener?.beforeTextChanged(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textChangeListener?.onTextChanged(s, start, before, count)
            }

            override fun afterTextChanged(s: Editable?) {
                textAttrChangeListener?.callback()
                textChangeListener?.afterTextChanged(s)
            }
        })
    }

    fun setLeftIconClickListener(leftIconClickListener: OnClickListener) {
        this.leftIconClickListener = leftIconClickListener
    }

    private fun setLeftIconClickListener() {
        binding.ivLeftIcon.setOnClickListener { leftIconClickListener?.onClick(it) }
    }

    fun setHint(resId: Int) {
        binding.et.hint = resources.getString(resId)
    }

    fun setLeftIcon(@DrawableRes resId: Int) {
        binding.ivLeftIcon.setImageResource(resId)
    }

    fun setLeftIconVisibility(visibility: Boolean) {
        binding.ivLeftIcon.visibility = if (visibility) VISIBLE else GONE
    }

}

interface VoidCallback {
    fun callback()
}