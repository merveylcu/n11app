package com.merveylcu.n11app.ui.dialog

import com.merveylcu.n11app.ui.base.VMState

sealed class DialogVMState : VMState {
    class OnClickedPositiveButton : VMState
    class OnClickedNegativeButton : VMState
}