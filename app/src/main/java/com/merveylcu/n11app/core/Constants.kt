package com.merveylcu.n11app.core

import com.merveylcu.n11app.ui.base.BaseActivity

class Constants {

    object App {
        var latestActivity: BaseActivity<*, *>? = null
    }

    object Database {
        const val name = "UsersDatabase"
        const val userTable = "Users"
    }

    object Url {
        const val base = "https://api.github.com/"
    }

    object Session {
        const val token = "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"
    }

}