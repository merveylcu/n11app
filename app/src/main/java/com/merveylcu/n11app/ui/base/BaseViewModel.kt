package com.merveylcu.n11app.ui.base

import androidx.lifecycle.ViewModel
import com.merveylcu.n11app.util.listener.SingleLiveEvent

open class BaseViewModel : ViewModel() {

    val state = SingleLiveEvent<VMState>()

}