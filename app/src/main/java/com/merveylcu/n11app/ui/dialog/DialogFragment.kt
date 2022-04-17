package com.merveylcu.n11app.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.merveylcu.n11app.databinding.DialogViewBinding
import com.merveylcu.n11app.ui.base.BaseDialogFragment
import com.merveylcu.n11app.ui.base.VMState
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogFragment private constructor(private val builder: Builder) : BaseDialogFragment() {

    private val viewModel: DialogViewModel by viewModel()
    private lateinit var binding: DialogViewBinding

    override fun getVM() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogViewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        viewModel.initUi(builder)
        binding.tvMessage.text = viewModel.messageText.value ?: ""
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        isCancelable = builder.isCancellable()
    }

    override fun onChangedScreenState(state: VMState) {
        super.onChangedScreenState(state)
        when (state) {
            is DialogVMState.OnClickedPositiveButton -> {
                dismiss()
                builder.getPositiveButtonAction()?.invoke()
            }
            is DialogVMState.OnClickedNegativeButton -> {
                dismiss()
                builder.getNegativeButtonAction()?.invoke()
            }
        }
    }

    fun show(fragmentManager: FragmentManager) {
        this.show(fragmentManager, "dialog")
    }

    companion object {
        fun build(lambda: Builder.() -> Unit): DialogFragment {
            val builder = Builder()
            builder.lambda()
            return builder.build()
        }
    }

    class Builder {
        private var message: String? = null
        private var positiveButtonText: String? = null
        private var negativeButtonText: String? = null
        private var positiveButtonAction: (() -> Unit)? = null
        private var negativeButtonAction: (() -> Unit)? = null
        private var cancellable: Boolean = true

        fun message(message: () -> String) {
            this.message = message()
        }

        fun getMessage() = message


        fun positiveButtonText(positiveButtonText: () -> String) {
            this.positiveButtonText = positiveButtonText()
        }

        fun getPositiveButtonText() = positiveButtonText

        fun negativeButtonText(negativeButtonText: () -> String) {
            this.negativeButtonText = negativeButtonText()
        }

        fun getNegativeButtonText() = negativeButtonText

        fun positiveButtonAction(positiveButtonAction: () -> Unit) {
            this.positiveButtonAction = positiveButtonAction
        }

        fun getPositiveButtonAction() = positiveButtonAction

        fun negativeButtonAction(negativeButtonAction: () -> Unit) {
            this.negativeButtonAction = negativeButtonAction
        }

        fun getNegativeButtonAction() = negativeButtonAction

        fun isCancellable() = cancellable

        fun isCancellable(cancellable: () -> Boolean) {
            this.cancellable = cancellable()
        }

        fun build(): DialogFragment {
            return DialogFragment(this)
        }
    }
}