package com.merveylcu.n11app.ui.dialog

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.merveylcu.n11app.ui.base.BaseViewModel
import com.merveylcu.n11app.util.listener.OnSingleClickListener

class DialogViewModel : BaseViewModel() {

    val messageText = MutableLiveData("")

    val positiveButtonText = MutableLiveData("")
    val positiveButtonVisibility = MutableLiveData(View.GONE)
    val onClickedPositiveButton = OnSingleClickListener {
        state.value = DialogVMState.OnClickedPositiveButton()
    }

    val negativeButtonText = MutableLiveData("")
    val negativeButtonVisibility = MutableLiveData(View.GONE)
    val onClickedNegativeButton = OnSingleClickListener {
        state.value = DialogVMState.OnClickedNegativeButton()
    }

    fun initUi(builder: DialogFragment.Builder) {
        initMessage(builder.getMessage())
        initPositiveButton(builder.getPositiveButtonText())
        initNegativeButton(builder.getNegativeButtonText())
    }

    private fun initMessage(message: String?) {
        message?.let {
            messageText.value = it
        }
    }

    private fun initPositiveButton(text: String?) {
        text?.let {
            positiveButtonText.value = it
            positiveButtonVisibility.value = View.VISIBLE
        }
    }

    private fun initNegativeButton(text: String?) {
        text?.let {
            negativeButtonText.value = it
            negativeButtonVisibility.value = View.VISIBLE
        }
    }
}