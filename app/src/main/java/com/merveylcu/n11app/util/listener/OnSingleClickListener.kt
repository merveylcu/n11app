package com.merveylcu.n11app.util.listener

import android.os.SystemClock
import android.view.View

class OnSingleClickListener(
    private var defaultInterval: Int = 1000,
    private val onSingleCLick: () -> Unit
) : View.OnClickListener {

    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSingleCLick()
    }
}