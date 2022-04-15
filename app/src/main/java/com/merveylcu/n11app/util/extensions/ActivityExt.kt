package com.merveylcu.n11app.util.extensions

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.merveylcu.n11app.ui.dialog.DialogFragment

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showDialog(
    title: String,
    message: String,
    positiveButtonText: String,
    positiveButtonAction: (() -> Unit)? = null,
    negativeButtonText: String? = null,
    negativeButtonAction: (() -> Unit)? = null
) {
    DialogFragment.build {
        title { title }
        message { message }
        positiveButtonText { positiveButtonText }
        positiveButtonAction { positiveButtonAction?.invoke() }
        negativeButtonText?.let {
            negativeButtonText { negativeButtonText }
        }
        negativeButtonAction { negativeButtonAction?.invoke() }
    }.show(supportFragmentManager)
}

fun Activity.getStr(resId: Int): String {
    return resources.getString(resId)
}