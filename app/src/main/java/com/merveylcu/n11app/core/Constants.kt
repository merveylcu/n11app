package com.merveylcu.n11app.core

import com.merveylcu.n11app.ui.base.BaseActivity

class Constants {

    object App {
        var latestActivity: BaseActivity<*, *>? = null
    }

    object Url {
        const val base = "https://api.github.com/"
    }

}