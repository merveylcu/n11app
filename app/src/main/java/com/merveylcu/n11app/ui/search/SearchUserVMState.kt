package com.merveylcu.n11app.ui.search

import com.merveylcu.n11app.ui.base.VMState

interface SearchUserVMState : VMState {
    class SetUserList : SearchUserVMState
    class OpenUserDetail(val userName: String) : SearchUserVMState
}