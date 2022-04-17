package com.merveylcu.n11app.service.util

import com.merveylcu.n11app.R
import com.merveylcu.n11app.core.Constants
import com.merveylcu.n11app.util.extensions.getStr
import com.merveylcu.n11app.util.extensions.showDialog
import com.merveylcu.network.NetworkHandler

object NetworkUtil {

    init {
        val positiveButtonText = Constants.App.latestActivity!!.getStr(R.string.ok)
        NetworkHandler.setNetworkActions(
            onNetworkError = {
                Constants.App.latestActivity?.showDialog(
                    message = Constants.App.latestActivity!!.getStr(R.string.error_occurred),
                    positiveButtonText = positiveButtonText
                )
            },
            onNetworkConnectivityError = {
                val errorMessage =
                    Constants.App.latestActivity!!.getStr(R.string.no_network_connectivity)
                Constants.App.latestActivity?.showDialog(
                    message = errorMessage,
                    positiveButtonText = positiveButtonText
                )
                errorMessage
            },
            showLoading = {
                Constants.App.latestActivity?.showLoading()
            },
            hideLoading = {
                Constants.App.latestActivity?.hideLoading()
            },
            onError = { message ->
                Constants.App.latestActivity?.showDialog(
                    message = message,
                    positiveButtonText = positiveButtonText
                )
            }
        )
    }

    fun createHeader(): MutableMap<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Content-Type"] = "application/json"
        headerMap["access_token"] = Constants.Session.token
        return headerMap
    }

}